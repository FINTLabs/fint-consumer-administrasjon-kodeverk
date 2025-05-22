package no.fint.consumer.models.anlegg;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import no.fint.antlr.FintFilterService;
import no.fint.audit.FintAuditService;
import no.fint.cache.exceptions.CacheNotFoundException;
import no.fint.consumer.config.Constants;
import no.fint.consumer.config.ConsumerProps;
import no.fint.consumer.event.ConsumerEventUtil;
import no.fint.consumer.event.SynchronousEvents;
import no.fint.consumer.exceptions.*;
import no.fint.consumer.status.StatusCache;
import no.fint.consumer.utils.EventResponses;
import no.fint.consumer.utils.RestEndpoints;
import no.fint.event.model.Event;
import no.fint.event.model.HeaderConstants;
import no.fint.event.model.Operation;
import no.fint.event.model.Status;
import no.fint.model.administrasjon.kodeverk.KodeverkActions;
import no.fint.model.resource.administrasjon.kodeverk.AnleggResource;
import no.fint.model.resource.administrasjon.kodeverk.AnleggResources;
import no.fint.relations.FintRelationsMediaType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Slf4j
@Api(tags = {"Anlegg"})
@CrossOrigin
@RestController
@RequestMapping(name = "Anlegg", value = RestEndpoints.ANLEGG, produces = {FintRelationsMediaType.APPLICATION_HAL_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class AnleggController {

    private static final String ODATA_FILTER_QUERY_OPTION = "$filter=";

    @Autowired(required = false)
    private AnleggCacheService cacheService;

    @Autowired
    private FintAuditService fintAuditService;

    @Autowired
    private AnleggLinker linker;

    @Autowired
    private ConsumerProps props;

    @Autowired
    private StatusCache statusCache;

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SynchronousEvents synchronousEvents;

    @Autowired
    private FintFilterService fintFilterService;

    @GetMapping("/last-updated")
    public Map<String, String> getLastUpdated(@RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId) {
        if (cacheService == null) {
            throw new CacheDisabledException("Anlegg cache is disabled.");
        }
        if (props.isOverrideOrgId() || orgId == null) {
            orgId = props.getDefaultOrgId();
        }
        String lastUpdated = Long.toString(cacheService.getLastUpdated(orgId));
        return ImmutableMap.of("lastUpdated", lastUpdated);
    }

    @GetMapping("/cache/size")
    public ImmutableMap<String, Integer> getCacheSize(@RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId) {
        if (cacheService == null) {
            throw new CacheDisabledException("Anlegg cache is disabled.");
        }
        if (props.isOverrideOrgId() || orgId == null) {
            orgId = props.getDefaultOrgId();
        }
        return ImmutableMap.of("size", cacheService.getCacheSize(orgId));
    }

    @GetMapping
    public AnleggResources getAnlegg(
            @RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId,
            @RequestHeader(name = HeaderConstants.CLIENT, required = false) String client,
            @RequestParam(defaultValue = "0") long sinceTimeStamp,
            @RequestParam(defaultValue = "0") int size,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(required = false) String $filter,
            HttpServletRequest request) throws InterruptedException {
        if (cacheService == null) {
            if (StringUtils.isNotBlank($filter)) {
                return getAnleggByOdataFilter(client, orgId, $filter);
            }
            throw new CacheDisabledException("Anlegg cache is disabled.");
        }
        if (props.isOverrideOrgId() || orgId == null) {
            orgId = props.getDefaultOrgId();
        }
        if (client == null) {
            client = props.getDefaultClient();
        }
        log.debug("OrgId: {}, Client: {}", orgId, client);

        Event event = new Event(orgId, Constants.COMPONENT, KodeverkActions.GET_ALL_ANLEGG, client);
        event.setOperation(Operation.READ);
        if (StringUtils.isNotBlank(request.getQueryString())) {
            event.setQuery("?" + request.getQueryString());
        }
        fintAuditService.audit(event);
        fintAuditService.audit(event, Status.CACHE);

        Stream<AnleggResource> resources;
        if (size > 0 && offset >= 0 && sinceTimeStamp > 0) {
            resources = cacheService.streamSliceSince(orgId, sinceTimeStamp, offset, size);
        } else if (size > 0 && offset >= 0) {
            resources = cacheService.streamSlice(orgId, offset, size);
        } else if (sinceTimeStamp > 0) {
            resources = cacheService.streamSince(orgId, sinceTimeStamp);
        } else {
            resources = cacheService.streamAll(orgId);
        }

        fintAuditService.audit(event, Status.CACHE_RESPONSE, Status.SENT_TO_CLIENT);

        return linker.toResources(resources, offset, size, cacheService.getCacheSize(orgId));
    }

    @PostMapping("/$query")
    public AnleggResources getAnleggByQuery(
            @RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId,
            @RequestHeader(name = HeaderConstants.CLIENT, required = false) String client,
            @RequestParam(defaultValue = "0") long sinceTimeStamp,
            @RequestParam(defaultValue = "0") int size,
            @RequestParam(defaultValue = "0") int offset,
            @RequestBody(required = false) String query,
            HttpServletRequest request
    ) throws InterruptedException {
        return getAnlegg(orgId, client, sinceTimeStamp, size, offset, query, request);
    }

    private AnleggResources getAnleggByOdataFilter(
            String client, String orgId, String $filter
    ) throws InterruptedException {
        if (!fintFilterService.validate($filter))
            throw new IllegalArgumentException("OData Filter is not valid");

        if (props.isOverrideOrgId() || orgId == null) orgId = props.getDefaultOrgId();
        if (client == null) client = props.getDefaultClient();

        Event event = new Event(
                orgId, Constants.COMPONENT,
                KodeverkActions.GET_ANLEGG, client);
        event.setOperation(Operation.READ);
        event.setQuery(ODATA_FILTER_QUERY_OPTION.concat($filter));

        BlockingQueue<Event> queue = synchronousEvents.register(event);
        consumerEventUtil.send(event);

        Event response = EventResponses.handle(queue.poll(5, TimeUnit.MINUTES));
        if (response.getData() == null || response.getData().isEmpty())
            return new AnleggResources();

        ArrayList<AnleggResource> list = objectMapper.convertValue(
                response.getData(),
                new TypeReference<ArrayList<AnleggResource>>() {
                });
        fintAuditService.audit(response, Status.SENT_TO_CLIENT);
        list.forEach(r -> linker.mapAndResetLinks(r));
        return linker.toResources(list);
    }


    @GetMapping("/systemid/{id:.+}")
    public AnleggResource getAnleggBySystemId(
            @PathVariable String id,
            @RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId,
            @RequestHeader(name = HeaderConstants.CLIENT, required = false) String client) throws InterruptedException {
        if (props.isOverrideOrgId() || orgId == null) {
            orgId = props.getDefaultOrgId();
        }
        if (client == null) {
            client = props.getDefaultClient();
        }
        log.debug("systemId: {}, OrgId: {}, Client: {}", id, orgId, client);

        Event event = new Event(orgId, Constants.COMPONENT, KodeverkActions.GET_ANLEGG, client);
        event.setOperation(Operation.READ);
        event.setQuery("systemId/" + id);

        if (cacheService != null) {
            fintAuditService.audit(event);
            fintAuditService.audit(event, Status.CACHE);

            Optional<AnleggResource> anlegg = cacheService.getAnleggBySystemId(orgId, id);

            fintAuditService.audit(event, Status.CACHE_RESPONSE, Status.SENT_TO_CLIENT);

            return anlegg.map(linker::toResource).orElseThrow(() -> new EntityNotFoundException(id));

        } else {
            BlockingQueue<Event> queue = synchronousEvents.register(event);
            consumerEventUtil.send(event);

            Event response = EventResponses.handle(queue.poll(5, TimeUnit.MINUTES));

            if (response.getData() == null ||
                    response.getData().isEmpty()) throw new EntityNotFoundException(id);

            AnleggResource anlegg = objectMapper.convertValue(response.getData().get(0), AnleggResource.class);

            fintAuditService.audit(response, Status.SENT_TO_CLIENT);

            return linker.mapAndResetLinks(anlegg);
        }
    }


    //
    // Exception handlers
    //
    @ExceptionHandler(EventResponseException.class)
    public ResponseEntity handleEventResponseException(EventResponseException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getResponse());
    }

    @ExceptionHandler(UpdateEntityMismatchException.class)
    public ResponseEntity handleUpdateEntityMismatch(Exception e) {
        return ResponseEntity.badRequest().body(ErrorResponse.of(e));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(e));
    }

    @ExceptionHandler(CreateEntityMismatchException.class)
    public ResponseEntity handleCreateEntityMismatch(Exception e) {
        return ResponseEntity.badRequest().body(ErrorResponse.of(e));
    }

    @ExceptionHandler(EntityFoundException.class)
    public ResponseEntity handleEntityFound(Exception e) {
        return ResponseEntity.status(HttpStatus.FOUND).body(ErrorResponse.of(e));
    }

    @ExceptionHandler(CacheDisabledException.class)
    public ResponseEntity handleBadRequest(Exception e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ErrorResponse.of(e));
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity handleUnkownHost(Exception e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ErrorResponse.of(e));
    }

    @ExceptionHandler(CacheNotFoundException.class)
    public ResponseEntity handleCacheNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ErrorResponse.of(e));
    }

}


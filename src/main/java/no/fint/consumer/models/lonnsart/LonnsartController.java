package no.fint.consumer.models.lonnsart;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import no.fint.audit.FintAuditService;
import no.fint.consumer.config.Constants;
import no.fint.consumer.utils.RestEndpoints;
import no.fint.event.model.Event;
import no.fint.event.model.HeaderConstants;
import no.fint.event.model.Status;
import no.fint.model.administrasjon.kodeverk.KodeverkActions;
import no.fint.model.administrasjon.kodeverk.Lonnsart;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintRelationsMediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = RestEndpoints.LONNSART, produces = {FintRelationsMediaType.APPLICATION_HAL_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class LonnsartController {

    @Autowired
    private LonnsartCacheService cacheService;

    @Autowired
    private FintAuditService fintAuditService;

    @Autowired
    private LonnsartAssembler assembler;

    @GetMapping("/last-updated")
    public Map<String, String> getLastUpdated(@RequestHeader(HeaderConstants.ORG_ID) String orgId) {
        String lastUpdated = Long.toString(cacheService.getLastUpdated(orgId));
        return ImmutableMap.of("lastUpdated", lastUpdated);
    }

    @GetMapping("/cache/size")
    public ImmutableMap<String, Integer> getCacheSize(@RequestHeader(HeaderConstants.ORG_ID) String orgId) {
        return ImmutableMap.of("size", cacheService.getAll(orgId).size());
    }

    @PostMapping("/cache/rebuild")
    public void rebuildCache(@RequestHeader(HeaderConstants.ORG_ID) String orgId) {
        cacheService.rebuildCache(orgId);
    }

    @GetMapping
    public ResponseEntity getLonnsart(@RequestHeader(HeaderConstants.ORG_ID) String orgId,
                                      @RequestHeader(HeaderConstants.CLIENT) String client,
                                      @RequestParam(required = false) Long sinceTimeStamp) {
        log.info("OrgId: {}", orgId);
        log.info("Client: {}", client);
        log.info("SinceTimeStamp: {}", sinceTimeStamp);

        Event event = new Event(orgId, Constants.COMPONENT, KodeverkActions.GET_ALL_LONNSART, client);
        fintAuditService.audit(event);

        fintAuditService.audit(event, Status.CACHE);

        List<FintResource<Lonnsart>> lonnsart;
        if (sinceTimeStamp == null) {
            lonnsart = cacheService.getAll(orgId);
        } else {
            lonnsart = cacheService.getAll(orgId, sinceTimeStamp);
        }

        fintAuditService.audit(event, Status.CACHE_RESPONSE, Status.SENT_TO_CLIENT);

        return assembler.resources(lonnsart);
    }

    @GetMapping("/systemid/{id}")
    public ResponseEntity getLonnsart(@PathVariable String id,
                                      @RequestHeader(HeaderConstants.ORG_ID) String orgId,
                                      @RequestHeader(HeaderConstants.CLIENT) String client) {
        log.info("OrgId: {}", orgId);
        log.info("Client: {}", client);

        Event event = new Event(orgId, Constants.COMPONENT, KodeverkActions.GET_LONNSART, client);
        fintAuditService.audit(event);

        fintAuditService.audit(event, Status.CACHE);

        Optional<FintResource<Lonnsart>> lonnsart = cacheService.getArt(orgId, id);

        fintAuditService.audit(event, Status.CACHE_RESPONSE, Status.SENT_TO_CLIENT);

        if (lonnsart.isPresent()) {
            return assembler.resource(lonnsart.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


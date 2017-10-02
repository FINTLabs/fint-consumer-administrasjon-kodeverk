package no.fint.consumer.models.arbeidsforholdstype;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import no.fint.audit.FintAuditService;
import no.fint.consumer.config.Constants;
import no.fint.consumer.utils.RestEndpoints;
import no.fint.event.model.Event;
import no.fint.event.model.HeaderConstants;
import no.fint.event.model.Status;
import no.fint.model.administrasjon.kodeverk.Arbeidsforholdstype;
import no.fint.model.administrasjon.kodeverk.KodeverkActions;
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
@RequestMapping(value = RestEndpoints.ARBEIDSFORHOLDSTYPE, produces = {FintRelationsMediaType.APPLICATION_HAL_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class ArbeidsforholdstypeController {

    @Autowired
    private ArbeidsforholdstypeCacheService cacheService;

    @Autowired
    private FintAuditService fintAuditService;

    @Autowired
    private ArbeidsforholdstypeAssembler assembler;

    @GetMapping("/last-updated")
    public Map<String, String> getLastUpdated(@RequestHeader(value = HeaderConstants.ORG_ID, defaultValue = Constants.DEFAULT_HEADER_ORGID) String orgId) {
        String lastUpdated = Long.toString(cacheService.getLastUpdated(orgId));
        return ImmutableMap.of("lastUpdated", lastUpdated);
    }

    @GetMapping("/cache/size")
    public ImmutableMap<String, Integer> getCacheSize(@RequestHeader(value = HeaderConstants.ORG_ID, defaultValue = Constants.DEFAULT_HEADER_ORGID) String orgId) {
        return ImmutableMap.of("size", cacheService.getAll(orgId).size());
    }

    @PostMapping("/cache/rebuild")
    public void rebuildCache(@RequestHeader(value = HeaderConstants.ORG_ID, defaultValue = Constants.DEFAULT_HEADER_ORGID) String orgId) {
        cacheService.rebuildCache(orgId);
    }

    @GetMapping
    public ResponseEntity getArbeidforholdstype(@RequestHeader(value = HeaderConstants.ORG_ID, defaultValue = Constants.DEFAULT_HEADER_ORGID) String orgId,
                                                @RequestHeader(value = HeaderConstants.CLIENT, defaultValue = Constants.DEFAULT_HEADER_CLIENT) String client,
                                                @RequestParam(required = false) Long sinceTimeStamp) {
        log.info("OrgId: {}", orgId);
        log.info("Client: {}", client);
        log.info("SinceTimeStamp: {}", sinceTimeStamp);

        Event event = new Event(orgId, Constants.COMPONENT, KodeverkActions.GET_ALL_ARBEIDSFORHOLDSTYPE, client);
        fintAuditService.audit(event);

        fintAuditService.audit(event, Status.CACHE);

        List<FintResource<Arbeidsforholdstype>> arbeidforholdstype;
        if (sinceTimeStamp == null) {
            arbeidforholdstype = cacheService.getAll(orgId);
        } else {
            arbeidforholdstype = cacheService.getAll(orgId, sinceTimeStamp);
        }

        fintAuditService.audit(event, Status.CACHE_RESPONSE, Status.SENT_TO_CLIENT);

        return assembler.resources(arbeidforholdstype);
    }

    @GetMapping("/systemid/{id}")
    public ResponseEntity getArbeidforholdstype(@PathVariable String id,
                                                @RequestHeader(value = HeaderConstants.ORG_ID, defaultValue = Constants.DEFAULT_HEADER_ORGID) String orgId,
                                                @RequestHeader(value = HeaderConstants.CLIENT, defaultValue = Constants.DEFAULT_HEADER_CLIENT) String client) {
        log.info("OrgId: {}", orgId);
        log.info("Client: {}", client);

        Event event = new Event(orgId, Constants.COMPONENT, KodeverkActions.GET_ARBEIDSFORHOLDSTYPE, client);
        fintAuditService.audit(event);

        fintAuditService.audit(event, Status.CACHE);

        Optional<FintResource<Arbeidsforholdstype>> arbeidforholdstype = cacheService.getArbeidforholdstype(orgId, id);

        fintAuditService.audit(event, Status.CACHE_RESPONSE, Status.SENT_TO_CLIENT);

        if (arbeidforholdstype.isPresent()) {
            return assembler.resource(arbeidforholdstype.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


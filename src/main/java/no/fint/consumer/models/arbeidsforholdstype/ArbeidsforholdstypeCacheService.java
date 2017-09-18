package no.fint.consumer.models.arbeidsforholdstype;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import no.fint.cache.CacheService;
import no.fint.cache.FintCache;
import no.fint.consumer.config.Constants;
import no.fint.consumer.config.ConsumerProps;
import no.fint.consumer.event.ConsumerEventUtil;
import no.fint.event.model.Event;
import no.fint.model.administrasjon.kodeverk.Arbeidsforholdstype;
import no.fint.model.administrasjon.kodeverk.KodeverkActions;
import no.fint.model.relation.FintResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ArbeidsforholdstypeCacheService extends CacheService<FintResource<Arbeidsforholdstype>> {

    public static final String MODEL = Arbeidsforholdstype.class.getSimpleName().toLowerCase();

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ConsumerProps props;

    public ArbeidsforholdstypeCacheService() {
        super(MODEL, KodeverkActions.GET_ALL_ARBEIDSFORHOLDSTYPE);
    }

    @PostConstruct
    public void init() {
        Arrays.stream(props.getOrgs()).forEach(orgId -> {
            FintCache<FintResource<Arbeidsforholdstype>> cache = new FintCache<>();
            put(orgId, cache);
        });
    }

    @Scheduled(initialDelayString = ConsumerProps.CACHE_INITIALDELAY_ARBEIDFORHOLDSTYPE, fixedRateString = ConsumerProps.CACHE_FIXEDRATE_ARBEIDFORHOLDSTYPE)
    public void populateCacheAll() {
        Arrays.stream(props.getOrgs()).forEach(this::populateCache);
    }

    public void rebuildCache(String orgId) {
        flush(orgId);
        populateCache(orgId);
    }

    private void populateCache(String orgId) {
        log.info("Populating Arbeidforholdstype cache for {}", orgId);
        Event event = new Event(orgId, Constants.COMPONENT, KodeverkActions.GET_ALL_ARBEIDSFORHOLDSTYPE, Constants.CACHE_SERVICE);
        consumerEventUtil.send(event);
    }

    public Optional<FintResource<Arbeidsforholdstype>> getArbeidforholdstype(String orgId, String systemId) {
        return getOne(orgId, (fintResource) -> fintResource.getResource().getSystemId().getIdentifikatorverdi().equals(systemId));
    }

    @Override
    public void onAction(Event event) {
        update(event, new TypeReference<List<FintResource<Arbeidsforholdstype>>>() {
        });
    }
}

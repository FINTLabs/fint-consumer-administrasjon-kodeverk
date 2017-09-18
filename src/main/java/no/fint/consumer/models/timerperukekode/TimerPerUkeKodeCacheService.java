package no.fint.consumer.models.timerperukekode;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import no.fint.cache.CacheService;
import no.fint.cache.FintCache;
import no.fint.consumer.config.Constants;
import no.fint.consumer.config.ConsumerProps;
import no.fint.consumer.event.ConsumerEventUtil;
import no.fint.event.model.Event;
import no.fint.model.administrasjon.kodeverk.KodeverkActions;
import no.fint.model.administrasjon.kodeverk.TimerPerUkeKode;
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
public class TimerPerUkeKodeCacheService extends CacheService<FintResource<TimerPerUkeKode>> {

    public static final String MODEL = TimerPerUkeKode.class.getSimpleName().toLowerCase();

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ConsumerProps props;

    public TimerPerUkeKodeCacheService() {
        super(MODEL, KodeverkActions.GET_ALL_TIMERPERUKEKODE);
    }

    @PostConstruct
    public void init() {
        Arrays.stream(props.getOrgs()).forEach(orgId -> {
            FintCache<FintResource<TimerPerUkeKode>> cache = new FintCache<>();
            put(orgId, cache);
        });
    }

    @Scheduled(initialDelayString = ConsumerProps.CACHE_INITIALDELAY_TIMERPERUKEKODE, fixedRateString = ConsumerProps.CACHE_FIXEDRATE_TIMERPERUKEKODE)
    public void populateCacheAll() {
        Arrays.stream(props.getOrgs()).forEach(this::populateCache);
    }

    public void rebuildCache(String orgId) {
        flush(orgId);
        populateCache(orgId);
    }

    private void populateCache(String orgId) {
        log.info("Populating TimerPerUkeKode cache for {}", orgId);
        Event event = new Event(orgId, Constants.COMPONENT, KodeverkActions.GET_ALL_TIMERPERUKEKODE, Constants.CACHE_SERVICE);
        consumerEventUtil.send(event);
    }

    public Optional<FintResource<TimerPerUkeKode>> getTimerPerUkeKode(String orgId, String systemId) {
        return getOne(orgId, (fintResource) -> fintResource.getResource().getSystemId().getIdentifikatorverdi().equals(systemId));
    }

    @Override
    public void onAction(Event event) {
        update(event, new TypeReference<List<FintResource<TimerPerUkeKode>>>() {
        });
    }
}

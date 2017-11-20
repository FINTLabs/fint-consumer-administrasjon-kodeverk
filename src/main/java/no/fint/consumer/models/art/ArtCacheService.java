package no.fint.consumer.models.art;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import no.fint.cache.CacheService;
import no.fint.consumer.config.Constants;
import no.fint.consumer.config.ConsumerProps;
import no.fint.consumer.event.ConsumerEventUtil;
import no.fint.event.model.Event;
import no.fint.model.administrasjon.kodeverk.Art;
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
public class ArtCacheService extends CacheService<FintResource<Art>> {

    public static final String MODEL = Art.class.getSimpleName().toLowerCase();

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ConsumerProps props;

    public ArtCacheService() {
        super(MODEL, KodeverkActions.GET_ALL_ART);
    }

    @PostConstruct
    public void init() {
        Arrays.stream(props.getOrgs()).forEach(this::createCache);
    }

    @Scheduled(initialDelayString = ConsumerProps.CACHE_INITIALDELAY_ART, fixedRateString = ConsumerProps.CACHE_FIXEDRATE_ART)
    public void populateCacheAll() {
        Arrays.stream(props.getOrgs()).forEach(this::populateCache);
    }

    public void rebuildCache(String orgId) {
        flush(orgId);
        populateCache(orgId);
    }

    private void populateCache(String orgId) {
        log.info("Populating Art cache for {}", orgId);
        Event event = new Event(orgId, Constants.COMPONENT, KodeverkActions.GET_ALL_ART, Constants.CACHE_SERVICE);
        consumerEventUtil.send(event);
    }

    public Optional<FintResource<Art>> getArt(String orgId, String systemId) {
        return getOne(orgId, (fintResource) -> fintResource.getResource().getSystemId().getIdentifikatorverdi().equals(systemId));
    }

    @Override
    public void onAction(Event event) {
        update(event, new TypeReference<List<FintResource<Art>>>() {
        });
    }
}

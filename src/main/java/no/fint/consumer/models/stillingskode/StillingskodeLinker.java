package no.fint.consumer.models.stillingskode;

import no.fint.model.resource.administrasjon.kodeverk.StillingskodeResource;
import no.fint.model.resource.administrasjon.kodeverk.StillingskodeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class StillingskodeLinker extends FintLinker<StillingskodeResource> {

    public StillingskodeLinker() {
        super(StillingskodeResource.class);
    }

    public void mapLinks(StillingskodeResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public StillingskodeResources toResources(Collection<StillingskodeResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public StillingskodeResources toResources(Stream<StillingskodeResource> stream, int offset, int size, int totalItems) {
        StillingskodeResources resources = new StillingskodeResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(StillingskodeResource stillingskode) {
        return getAllSelfHrefs(stillingskode).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(StillingskodeResource stillingskode) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(stillingskode.getSystemId()) && !isEmpty(stillingskode.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(stillingskode.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(StillingskodeResource stillingskode) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(stillingskode.getSystemId()) && !isEmpty(stillingskode.getSystemId().getIdentifikatorverdi())) {
            builder.add(stillingskode.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


package no.fint.consumer.models.formal;

import no.fint.model.resource.administrasjon.kodeverk.FormalResource;
import no.fint.model.resource.administrasjon.kodeverk.FormalResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class FormalLinker extends FintLinker<FormalResource> {

    public FormalLinker() {
        super(FormalResource.class);
    }

    public void mapLinks(FormalResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public FormalResources toResources(Collection<FormalResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public FormalResources toResources(Stream<FormalResource> stream, int offset, int size, int totalItems) {
        FormalResources resources = new FormalResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(FormalResource formal) {
        return getAllSelfHrefs(formal).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(FormalResource formal) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(formal.getSystemId()) && !isEmpty(formal.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(formal.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(FormalResource formal) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(formal.getSystemId()) && !isEmpty(formal.getSystemId().getIdentifikatorverdi())) {
            builder.add(formal.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


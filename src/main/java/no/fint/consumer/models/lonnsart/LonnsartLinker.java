package no.fint.consumer.models.lonnsart;

import no.fint.model.resource.administrasjon.kodeverk.LonnsartResource;
import no.fint.model.resource.administrasjon.kodeverk.LonnsartResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class LonnsartLinker extends FintLinker<LonnsartResource> {

    public LonnsartLinker() {
        super(LonnsartResource.class);
    }

    public void mapLinks(LonnsartResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public LonnsartResources toResources(Collection<LonnsartResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public LonnsartResources toResources(Stream<LonnsartResource> stream, int offset, int size, int totalItems) {
        LonnsartResources resources = new LonnsartResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(LonnsartResource lonnsart) {
        return getAllSelfHrefs(lonnsart).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(LonnsartResource lonnsart) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(lonnsart.getSystemId()) && !isEmpty(lonnsart.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(lonnsart.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(LonnsartResource lonnsart) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(lonnsart.getSystemId()) && !isEmpty(lonnsart.getSystemId().getIdentifikatorverdi())) {
            builder.add(lonnsart.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


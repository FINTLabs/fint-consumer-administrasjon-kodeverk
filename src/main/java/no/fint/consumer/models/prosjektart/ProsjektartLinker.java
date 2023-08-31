package no.fint.consumer.models.prosjektart;

import no.fint.model.resource.administrasjon.kodeverk.ProsjektartResource;
import no.fint.model.resource.administrasjon.kodeverk.ProsjektartResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class ProsjektartLinker extends FintLinker<ProsjektartResource> {

    public ProsjektartLinker() {
        super(ProsjektartResource.class);
    }

    public void mapLinks(ProsjektartResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public ProsjektartResources toResources(Collection<ProsjektartResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public ProsjektartResources toResources(Stream<ProsjektartResource> stream, int offset, int size, int totalItems) {
        ProsjektartResources resources = new ProsjektartResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(ProsjektartResource prosjektart) {
        return getAllSelfHrefs(prosjektart).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(ProsjektartResource prosjektart) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(prosjektart.getSystemId()) && !isEmpty(prosjektart.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(prosjektart.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(ProsjektartResource prosjektart) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(prosjektart.getSystemId()) && !isEmpty(prosjektart.getSystemId().getIdentifikatorverdi())) {
            builder.add(prosjektart.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


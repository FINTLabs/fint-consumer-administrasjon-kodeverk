package no.fint.consumer.models.art;

import no.fint.model.resource.administrasjon.kodeverk.ArtResource;
import no.fint.model.resource.administrasjon.kodeverk.ArtResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class ArtLinker extends FintLinker<ArtResource> {

    public ArtLinker() {
        super(ArtResource.class);
    }

    public void mapLinks(ArtResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public ArtResources toResources(Collection<ArtResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public ArtResources toResources(Stream<ArtResource> stream, int offset, int size, int totalItems) {
        ArtResources resources = new ArtResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(ArtResource art) {
        return getAllSelfHrefs(art).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(ArtResource art) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(art.getSystemId()) && !isEmpty(art.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(art.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(ArtResource art) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(art.getSystemId()) && !isEmpty(art.getSystemId().getIdentifikatorverdi())) {
            builder.add(art.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


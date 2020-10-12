package no.fint.consumer.models.funksjon;

import no.fint.model.resource.administrasjon.kodeverk.FunksjonResource;
import no.fint.model.resource.administrasjon.kodeverk.FunksjonResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class FunksjonLinker extends FintLinker<FunksjonResource> {

    public FunksjonLinker() {
        super(FunksjonResource.class);
    }

    public void mapLinks(FunksjonResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public FunksjonResources toResources(Collection<FunksjonResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public FunksjonResources toResources(Stream<FunksjonResource> stream, int offset, int size, int totalItems) {
        FunksjonResources resources = new FunksjonResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(FunksjonResource funksjon) {
        return getAllSelfHrefs(funksjon).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(FunksjonResource funksjon) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(funksjon.getSystemId()) && !isEmpty(funksjon.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(funksjon.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(FunksjonResource funksjon) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(funksjon.getSystemId()) && !isEmpty(funksjon.getSystemId().getIdentifikatorverdi())) {
            builder.add(funksjon.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


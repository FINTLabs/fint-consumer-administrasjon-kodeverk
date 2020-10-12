package no.fint.consumer.models.fravarsgrunn;

import no.fint.model.resource.administrasjon.kodeverk.FravarsgrunnResource;
import no.fint.model.resource.administrasjon.kodeverk.FravarsgrunnResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class FravarsgrunnLinker extends FintLinker<FravarsgrunnResource> {

    public FravarsgrunnLinker() {
        super(FravarsgrunnResource.class);
    }

    public void mapLinks(FravarsgrunnResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public FravarsgrunnResources toResources(Collection<FravarsgrunnResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public FravarsgrunnResources toResources(Stream<FravarsgrunnResource> stream, int offset, int size, int totalItems) {
        FravarsgrunnResources resources = new FravarsgrunnResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(FravarsgrunnResource fravarsgrunn) {
        return getAllSelfHrefs(fravarsgrunn).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(FravarsgrunnResource fravarsgrunn) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(fravarsgrunn.getSystemId()) && !isEmpty(fravarsgrunn.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(fravarsgrunn.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(FravarsgrunnResource fravarsgrunn) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(fravarsgrunn.getSystemId()) && !isEmpty(fravarsgrunn.getSystemId().getIdentifikatorverdi())) {
            builder.add(fravarsgrunn.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


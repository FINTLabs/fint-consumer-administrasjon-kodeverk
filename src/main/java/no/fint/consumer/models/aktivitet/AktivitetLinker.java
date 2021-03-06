package no.fint.consumer.models.aktivitet;

import no.fint.model.resource.administrasjon.kodeverk.AktivitetResource;
import no.fint.model.resource.administrasjon.kodeverk.AktivitetResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class AktivitetLinker extends FintLinker<AktivitetResource> {

    public AktivitetLinker() {
        super(AktivitetResource.class);
    }

    public void mapLinks(AktivitetResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public AktivitetResources toResources(Collection<AktivitetResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public AktivitetResources toResources(Stream<AktivitetResource> stream, int offset, int size, int totalItems) {
        AktivitetResources resources = new AktivitetResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(AktivitetResource aktivitet) {
        return getAllSelfHrefs(aktivitet).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(AktivitetResource aktivitet) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(aktivitet.getSystemId()) && !isEmpty(aktivitet.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(aktivitet.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(AktivitetResource aktivitet) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(aktivitet.getSystemId()) && !isEmpty(aktivitet.getSystemId().getIdentifikatorverdi())) {
            builder.add(aktivitet.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


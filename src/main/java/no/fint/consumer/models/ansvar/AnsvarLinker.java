package no.fint.consumer.models.ansvar;

import no.fint.model.resource.administrasjon.kodeverk.AnsvarResource;
import no.fint.model.resource.administrasjon.kodeverk.AnsvarResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class AnsvarLinker extends FintLinker<AnsvarResource> {

    public AnsvarLinker() {
        super(AnsvarResource.class);
    }

    public void mapLinks(AnsvarResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public AnsvarResources toResources(Collection<AnsvarResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public AnsvarResources toResources(Stream<AnsvarResource> stream, int offset, int size, int totalItems) {
        AnsvarResources resources = new AnsvarResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(AnsvarResource ansvar) {
        return getAllSelfHrefs(ansvar).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(AnsvarResource ansvar) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(ansvar.getSystemId()) && !isEmpty(ansvar.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(ansvar.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(AnsvarResource ansvar) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(ansvar.getSystemId()) && !isEmpty(ansvar.getSystemId().getIdentifikatorverdi())) {
            builder.add(ansvar.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


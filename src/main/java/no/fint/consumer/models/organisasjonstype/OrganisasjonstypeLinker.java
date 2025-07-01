package no.fint.consumer.models.organisasjonstype;

import no.fint.model.resource.administrasjon.kodeverk.OrganisasjonstypeResource;
import no.fint.model.resource.administrasjon.kodeverk.OrganisasjonstypeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class OrganisasjonstypeLinker extends FintLinker<OrganisasjonstypeResource> {

    public OrganisasjonstypeLinker() {
        super(OrganisasjonstypeResource.class);
    }

    public void mapLinks(OrganisasjonstypeResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public OrganisasjonstypeResources toResources(Collection<OrganisasjonstypeResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public OrganisasjonstypeResources toResources(Stream<OrganisasjonstypeResource> stream, int offset, int size, int totalItems) {
        OrganisasjonstypeResources resources = new OrganisasjonstypeResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(OrganisasjonstypeResource organisasjonstype) {
        return getAllSelfHrefs(organisasjonstype).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(OrganisasjonstypeResource organisasjonstype) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(organisasjonstype.getSystemId()) && !isEmpty(organisasjonstype.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(organisasjonstype.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(OrganisasjonstypeResource organisasjonstype) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(organisasjonstype.getSystemId()) && !isEmpty(organisasjonstype.getSystemId().getIdentifikatorverdi())) {
            builder.add(organisasjonstype.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


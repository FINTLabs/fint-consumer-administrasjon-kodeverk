package no.fint.consumer.models.lonnsart;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.LonnsartResource;
import no.fint.model.resource.administrasjon.kodeverk.LonnsartResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

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
        LonnsartResources resources = new LonnsartResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(LonnsartResource lonnsart) {
        if (!isNull(lonnsart.getSystemId()) && !isEmpty(lonnsart.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(lonnsart.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(LonnsartResource lonnsart) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(lonnsart.getSystemId()) && !isEmpty(lonnsart.getSystemId().getIdentifikatorverdi())) {
            builder.add(lonnsart.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


package no.fint.consumer.models.lonnsart;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.LonnsartResource;
import no.fint.model.resource.administrasjon.kodeverk.LonnsartResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

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
        if (lonnsart.getSystemId() != null && lonnsart.getSystemId().getIdentifikatorverdi() != null) {
            return createHrefWithId(lonnsart.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}


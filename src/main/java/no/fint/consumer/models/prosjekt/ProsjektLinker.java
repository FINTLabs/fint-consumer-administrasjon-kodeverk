package no.fint.consumer.models.prosjekt;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.ProsjektResource;
import no.fint.model.resource.administrasjon.kodeverk.ProsjektResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ProsjektLinker extends FintLinker<ProsjektResource> {

    public ProsjektLinker() {
        super(ProsjektResource.class);
    }

    public void mapLinks(ProsjektResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public ProsjektResources toResources(Collection<ProsjektResource> collection) {
        ProsjektResources resources = new ProsjektResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(ProsjektResource prosjekt) {
        if (prosjekt.getSystemId() != null && prosjekt.getSystemId().getIdentifikatorverdi() != null) {
            return createHrefWithId(prosjekt.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}


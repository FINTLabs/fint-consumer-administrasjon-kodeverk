package no.fint.consumer.models.stillingskode;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.StillingskodeResource;
import no.fint.model.resource.administrasjon.kodeverk.StillingskodeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class StillingskodeLinker extends FintLinker<StillingskodeResource> {

    public StillingskodeLinker() {
        super(StillingskodeResource.class);
    }

    public void mapLinks(StillingskodeResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public StillingskodeResources toResources(Collection<StillingskodeResource> collection) {
        StillingskodeResources resources = new StillingskodeResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(StillingskodeResource stillingskode) {
        if (stillingskode.getSystemId() != null && stillingskode.getSystemId().getIdentifikatorverdi() != null) {
            return createHrefWithId(stillingskode.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}


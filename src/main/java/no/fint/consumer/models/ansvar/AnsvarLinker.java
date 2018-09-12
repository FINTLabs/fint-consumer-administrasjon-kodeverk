package no.fint.consumer.models.ansvar;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.AnsvarResource;
import no.fint.model.resource.administrasjon.kodeverk.AnsvarResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

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
        AnsvarResources resources = new AnsvarResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(AnsvarResource ansvar) {
        if (ansvar.getSystemId() != null && ansvar.getSystemId().getIdentifikatorverdi() != null) {
            return createHrefWithId(ansvar.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}


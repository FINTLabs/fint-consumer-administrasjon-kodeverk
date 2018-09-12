package no.fint.consumer.models.fravarsgrunn;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.FravarsgrunnResource;
import no.fint.model.resource.administrasjon.kodeverk.FravarsgrunnResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

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
        FravarsgrunnResources resources = new FravarsgrunnResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(FravarsgrunnResource fravarsgrunn) {
        if (fravarsgrunn.getSystemId() != null && fravarsgrunn.getSystemId().getIdentifikatorverdi() != null) {
            return createHrefWithId(fravarsgrunn.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}


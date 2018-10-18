package no.fint.consumer.models.funksjon;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.FunksjonResource;
import no.fint.model.resource.administrasjon.kodeverk.FunksjonResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class FunksjonLinker extends FintLinker<FunksjonResource> {

    public FunksjonLinker() {
        super(FunksjonResource.class);
    }

    public void mapLinks(FunksjonResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public FunksjonResources toResources(Collection<FunksjonResource> collection) {
        FunksjonResources resources = new FunksjonResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(FunksjonResource funksjon) {
        if (!isNull(funksjon.getSystemId()) && !isEmpty(funksjon.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(funksjon.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}


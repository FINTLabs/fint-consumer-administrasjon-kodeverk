package no.fint.consumer.models.uketimetall;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.UketimetallResource;
import no.fint.model.resource.administrasjon.kodeverk.UketimetallResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class UketimetallLinker extends FintLinker<UketimetallResource> {

    public UketimetallLinker() {
        super(UketimetallResource.class);
    }

    public void mapLinks(UketimetallResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public UketimetallResources toResources(Collection<UketimetallResource> collection) {
        UketimetallResources resources = new UketimetallResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(UketimetallResource uketimetall) {
        if (!isNull(uketimetall.getSystemId()) && !isEmpty(uketimetall.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(uketimetall.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}


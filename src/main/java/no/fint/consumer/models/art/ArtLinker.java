package no.fint.consumer.models.art;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.ArtResource;
import no.fint.model.resource.administrasjon.kodeverk.ArtResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class ArtLinker extends FintLinker<ArtResource> {

    public ArtLinker() {
        super(ArtResource.class);
    }

    public void mapLinks(ArtResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public ArtResources toResources(Collection<ArtResource> collection) {
        ArtResources resources = new ArtResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(ArtResource art) {
        if (!isNull(art.getSystemId()) && !isEmpty(art.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(art.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}


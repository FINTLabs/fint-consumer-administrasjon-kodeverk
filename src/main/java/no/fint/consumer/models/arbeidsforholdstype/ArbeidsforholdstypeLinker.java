package no.fint.consumer.models.arbeidsforholdstype;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.ArbeidsforholdstypeResource;
import no.fint.model.resource.administrasjon.kodeverk.ArbeidsforholdstypeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ArbeidsforholdstypeLinker extends FintLinker<ArbeidsforholdstypeResource> {

    public ArbeidsforholdstypeLinker() {
        super(ArbeidsforholdstypeResource.class);
    }

    public void mapLinks(ArbeidsforholdstypeResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public ArbeidsforholdstypeResources toResources(Collection<ArbeidsforholdstypeResource> collection) {
        ArbeidsforholdstypeResources resources = new ArbeidsforholdstypeResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(ArbeidsforholdstypeResource arbeidsforholdstype) {
        if (arbeidsforholdstype.getSystemId() != null && arbeidsforholdstype.getSystemId().getIdentifikatorverdi() != null) {
            return createHrefWithId(arbeidsforholdstype.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}


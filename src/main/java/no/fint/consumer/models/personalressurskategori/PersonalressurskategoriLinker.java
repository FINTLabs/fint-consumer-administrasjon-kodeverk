package no.fint.consumer.models.personalressurskategori;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.PersonalressurskategoriResource;
import no.fint.model.resource.administrasjon.kodeverk.PersonalressurskategoriResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PersonalressurskategoriLinker extends FintLinker<PersonalressurskategoriResource> {

    public PersonalressurskategoriLinker() {
        super(PersonalressurskategoriResource.class);
    }

    public void mapLinks(PersonalressurskategoriResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public PersonalressurskategoriResources toResources(Collection<PersonalressurskategoriResource> collection) {
        PersonalressurskategoriResources resources = new PersonalressurskategoriResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(PersonalressurskategoriResource personalressurskategori) {
        if (personalressurskategori.getSystemId() != null && personalressurskategori.getSystemId().getIdentifikatorverdi() != null) {
            return createHrefWithId(personalressurskategori.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}


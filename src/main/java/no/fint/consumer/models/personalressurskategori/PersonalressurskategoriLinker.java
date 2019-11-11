package no.fint.consumer.models.personalressurskategori;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.PersonalressurskategoriResource;
import no.fint.model.resource.administrasjon.kodeverk.PersonalressurskategoriResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


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
        if (!isNull(personalressurskategori.getSystemId()) && !isEmpty(personalressurskategori.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(personalressurskategori.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(PersonalressurskategoriResource personalressurskategori) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(personalressurskategori.getSystemId()) && !isEmpty(personalressurskategori.getSystemId().getIdentifikatorverdi())) {
            builder.add(personalressurskategori.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


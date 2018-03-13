package no.fint.consumer.models.personalressurskategori;

import no.fint.model.administrasjon.kodeverk.Personalressurskategori;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class PersonalressurskategoriAssembler extends FintResourceAssembler<Personalressurskategori> {

    public PersonalressurskategoriAssembler() {
        super(PersonalressurskategoriController.class);
    }


    @Override
    public FintResourceSupport assemble(Personalressurskategori personalressurskategori , FintResource<Personalressurskategori> fintResource) {
        return createResourceWithId(personalressurskategori.getSystemId().getIdentifikatorverdi(), fintResource, "systemid");
    }
    
    
    
}


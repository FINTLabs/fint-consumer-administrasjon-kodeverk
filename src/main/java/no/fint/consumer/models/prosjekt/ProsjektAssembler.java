package no.fint.consumer.models.prosjekt;

import no.fint.model.administrasjon.kodeverk.Prosjekt;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class ProsjektAssembler extends FintResourceAssembler<Prosjekt> {

    public ProsjektAssembler() {
        super(ProsjektController.class);
    }


    @Override
    public FintResourceSupport assemble(Prosjekt prosjekt , FintResource<Prosjekt> fintResource) {
        return createResourceWithId(prosjekt.getSystemId().getIdentifikatorverdi(), fintResource, "systemid");
    }
    
    
    
}


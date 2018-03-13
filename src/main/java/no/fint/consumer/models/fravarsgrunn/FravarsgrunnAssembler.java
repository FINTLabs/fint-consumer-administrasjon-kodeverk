package no.fint.consumer.models.fravarsgrunn;

import no.fint.model.administrasjon.kodeverk.Fravarsgrunn;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class FravarsgrunnAssembler extends FintResourceAssembler<Fravarsgrunn> {

    public FravarsgrunnAssembler() {
        super(FravarsgrunnController.class);
    }


    @Override
    public FintResourceSupport assemble(Fravarsgrunn fravarsgrunn , FintResource<Fravarsgrunn> fintResource) {
        return createResourceWithId(fravarsgrunn.getSystemId().getIdentifikatorverdi(), fintResource, "systemid");
    }
    
    
    
}


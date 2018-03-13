package no.fint.consumer.models.fravarstype;

import no.fint.model.administrasjon.kodeverk.Fravarstype;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class FravarstypeAssembler extends FintResourceAssembler<Fravarstype> {

    public FravarstypeAssembler() {
        super(FravarstypeController.class);
    }


    @Override
    public FintResourceSupport assemble(Fravarstype fravarstype , FintResource<Fravarstype> fintResource) {
        return createResourceWithId(fravarstype.getSystemId().getIdentifikatorverdi(), fintResource, "systemid");
    }
    
    
    
}


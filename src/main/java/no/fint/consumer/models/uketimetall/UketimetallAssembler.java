package no.fint.consumer.models.uketimetall;

import no.fint.model.administrasjon.kodeverk.Uketimetall;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class UketimetallAssembler extends FintResourceAssembler<Uketimetall> {

    public UketimetallAssembler() {
        super(UketimetallController.class);
    }


    @Override
    public FintResourceSupport assemble(Uketimetall uketimetall , FintResource<Uketimetall> fintResource) {
        return createResourceWithId(uketimetall.getSystemId().getIdentifikatorverdi(), fintResource, "systemid");
    }
    
    
    
}


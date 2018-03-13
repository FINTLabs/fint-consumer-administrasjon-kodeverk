package no.fint.consumer.models.funksjon;

import no.fint.model.administrasjon.kodeverk.Funksjon;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class FunksjonAssembler extends FintResourceAssembler<Funksjon> {

    public FunksjonAssembler() {
        super(FunksjonController.class);
    }


    @Override
    public FintResourceSupport assemble(Funksjon funksjon , FintResource<Funksjon> fintResource) {
        return createResourceWithId(funksjon.getSystemId().getIdentifikatorverdi(), fintResource, "systemid");
    }
    
    
    
}


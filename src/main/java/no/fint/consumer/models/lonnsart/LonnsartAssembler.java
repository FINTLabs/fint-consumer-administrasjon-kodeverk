package no.fint.consumer.models.lonnsart;

import no.fint.model.administrasjon.kodeverk.Lonnsart;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class LonnsartAssembler extends FintResourceAssembler<Lonnsart> {

    public LonnsartAssembler() {
        super(LonnsartController.class);
    }


    @Override
    public FintResourceSupport assemble(Lonnsart lonnsart , FintResource<Lonnsart> fintResource) {
        return createResourceWithId(lonnsart.getSystemId().getIdentifikatorverdi(), fintResource, "systemid");
    }
    
    
    
}


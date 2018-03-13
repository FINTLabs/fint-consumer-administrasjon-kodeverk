package no.fint.consumer.models.arbeidsforholdstype;

import no.fint.model.administrasjon.kodeverk.Arbeidsforholdstype;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class ArbeidsforholdstypeAssembler extends FintResourceAssembler<Arbeidsforholdstype> {

    public ArbeidsforholdstypeAssembler() {
        super(ArbeidsforholdstypeController.class);
    }


    @Override
    public FintResourceSupport assemble(Arbeidsforholdstype arbeidsforholdstype , FintResource<Arbeidsforholdstype> fintResource) {
        return createResourceWithId(arbeidsforholdstype.getSystemId().getIdentifikatorverdi(), fintResource, "systemid");
    }
    
    
    
}


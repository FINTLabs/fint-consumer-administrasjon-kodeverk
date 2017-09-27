package no.fint.consumer.models.ansvar;

import no.fint.model.administrasjon.kodeverk.Ansvar;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class AnsvarAssembler extends FintResourceAssembler<Ansvar> {

    public AnsvarAssembler() {
        super(AnsvarController.class);
    }

    @Override
    public FintResourceSupport assemble(Ansvar ansvar, FintResource<Ansvar> fintResource) {
        return createResourceWithId(ansvar.getSystemId().getIdentifikatorverdi(), fintResource, "systemid");
    }
}


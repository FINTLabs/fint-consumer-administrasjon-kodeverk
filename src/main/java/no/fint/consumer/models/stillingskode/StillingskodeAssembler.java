package no.fint.consumer.models.stillingskode;

import no.fint.model.administrasjon.kodeverk.Stillingskode;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class StillingskodeAssembler extends FintResourceAssembler<Stillingskode> {

    public StillingskodeAssembler() {
        super(StillingskodeController.class);
    }

    @Override
    public FintResourceSupport assemble(Stillingskode stillingskode , FintResource<Stillingskode> fintResource) {
        return createResourceWithId(stillingskode.getSystemId().getIdentifikatorverdi(), fintResource, "systemid");
    }
}


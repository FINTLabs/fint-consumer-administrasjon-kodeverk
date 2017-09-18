package no.fint.consumer.models.timerperukekode;

import no.fint.model.administrasjon.kodeverk.TimerPerUkeKode;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class TimerPerUkeKodeAssembler extends FintResourceAssembler<TimerPerUkeKode> {

    public TimerPerUkeKodeAssembler() {
        super(TimerPerUkeKodeController.class);
    }

    @Override
    public FintResourceSupport assemble(TimerPerUkeKode timerperukekode, FintResource<TimerPerUkeKode> fintResource) {
        return createResourceWithId(timerperukekode.getSystemId().getIdentifikatorverdi(), fintResource, "systemid");
    }
}


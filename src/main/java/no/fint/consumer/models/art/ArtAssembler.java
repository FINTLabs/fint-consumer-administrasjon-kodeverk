package no.fint.consumer.models.art;

import no.fint.model.administrasjon.kodeverk.Art;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class ArtAssembler extends FintResourceAssembler<Art> {

    public ArtAssembler() {
        super(ArtController.class);
    }

    @Override
    public FintResourceSupport assemble(Art art, FintResource<Art> fintResource) {
        return createResourceWithId(art.getSystemId().getIdentifikatorverdi(), fintResource, "systemid");
    }
}


package no.fint.consumer.models.aktivitet;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.AktivitetResource;
import no.fint.model.resource.administrasjon.kodeverk.AktivitetResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class AktivitetLinker extends FintLinker<AktivitetResource> {

    public AktivitetLinker() {
        super(AktivitetResource.class);
    }

    public void mapLinks(AktivitetResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public AktivitetResources toResources(Collection<AktivitetResource> collection) {
        AktivitetResources resources = new AktivitetResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(AktivitetResource aktivitet) {
        if (!isNull(aktivitet.getSystemId()) && !isEmpty(aktivitet.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(aktivitet.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(AktivitetResource aktivitet) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(aktivitet.getSystemId()) && !isEmpty(aktivitet.getSystemId().getIdentifikatorverdi())) {
            builder.add(aktivitet.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


package no.fint.consumer.models.kontrakt;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.KontraktResource;
import no.fint.model.resource.administrasjon.kodeverk.KontraktResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class KontraktLinker extends FintLinker<KontraktResource> {

    public KontraktLinker() {
        super(KontraktResource.class);
    }

    public void mapLinks(KontraktResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public KontraktResources toResources(Collection<KontraktResource> collection) {
        KontraktResources resources = new KontraktResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(KontraktResource kontrakt) {
        if (!isNull(kontrakt.getSystemId()) && !isEmpty(kontrakt.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(kontrakt.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(KontraktResource kontrakt) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(kontrakt.getSystemId()) && !isEmpty(kontrakt.getSystemId().getIdentifikatorverdi())) {
            builder.add(kontrakt.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


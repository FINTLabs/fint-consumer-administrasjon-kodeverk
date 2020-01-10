package no.fint.consumer.models.ramme;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.RammeResource;
import no.fint.model.resource.administrasjon.kodeverk.RammeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class RammeLinker extends FintLinker<RammeResource> {

    public RammeLinker() {
        super(RammeResource.class);
    }

    public void mapLinks(RammeResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public RammeResources toResources(Collection<RammeResource> collection) {
        RammeResources resources = new RammeResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(RammeResource ramme) {
        if (!isNull(ramme.getSystemId()) && !isEmpty(ramme.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(ramme.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(RammeResource ramme) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(ramme.getSystemId()) && !isEmpty(ramme.getSystemId().getIdentifikatorverdi())) {
            builder.add(ramme.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


package no.fint.consumer.models.stillingskode;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.StillingskodeResource;
import no.fint.model.resource.administrasjon.kodeverk.StillingskodeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class StillingskodeLinker extends FintLinker<StillingskodeResource> {

    public StillingskodeLinker() {
        super(StillingskodeResource.class);
    }

    public void mapLinks(StillingskodeResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public StillingskodeResources toResources(Collection<StillingskodeResource> collection) {
        StillingskodeResources resources = new StillingskodeResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(StillingskodeResource stillingskode) {
        if (!isNull(stillingskode.getSystemId()) && !isEmpty(stillingskode.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(stillingskode.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(StillingskodeResource stillingskode) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(stillingskode.getSystemId()) && !isEmpty(stillingskode.getSystemId().getIdentifikatorverdi())) {
            builder.add(stillingskode.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


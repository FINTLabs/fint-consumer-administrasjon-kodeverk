package no.fint.consumer.models.prosjekt;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.ProsjektResource;
import no.fint.model.resource.administrasjon.kodeverk.ProsjektResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class ProsjektLinker extends FintLinker<ProsjektResource> {

    public ProsjektLinker() {
        super(ProsjektResource.class);
    }

    public void mapLinks(ProsjektResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public ProsjektResources toResources(Collection<ProsjektResource> collection) {
        ProsjektResources resources = new ProsjektResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(ProsjektResource prosjekt) {
        if (!isNull(prosjekt.getSystemId()) && !isEmpty(prosjekt.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(prosjekt.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(ProsjektResource prosjekt) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(prosjekt.getSystemId()) && !isEmpty(prosjekt.getSystemId().getIdentifikatorverdi())) {
            builder.add(prosjekt.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


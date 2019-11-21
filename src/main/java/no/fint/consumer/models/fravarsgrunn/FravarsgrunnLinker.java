package no.fint.consumer.models.fravarsgrunn;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.FravarsgrunnResource;
import no.fint.model.resource.administrasjon.kodeverk.FravarsgrunnResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class FravarsgrunnLinker extends FintLinker<FravarsgrunnResource> {

    public FravarsgrunnLinker() {
        super(FravarsgrunnResource.class);
    }

    public void mapLinks(FravarsgrunnResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public FravarsgrunnResources toResources(Collection<FravarsgrunnResource> collection) {
        FravarsgrunnResources resources = new FravarsgrunnResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(FravarsgrunnResource fravarsgrunn) {
        if (!isNull(fravarsgrunn.getSystemId()) && !isEmpty(fravarsgrunn.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(fravarsgrunn.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(FravarsgrunnResource fravarsgrunn) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(fravarsgrunn.getSystemId()) && !isEmpty(fravarsgrunn.getSystemId().getIdentifikatorverdi())) {
            builder.add(fravarsgrunn.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


package no.fint.consumer.models.diverse;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.DiverseResource;
import no.fint.model.resource.administrasjon.kodeverk.DiverseResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class DiverseLinker extends FintLinker<DiverseResource> {

    public DiverseLinker() {
        super(DiverseResource.class);
    }

    public void mapLinks(DiverseResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public DiverseResources toResources(Collection<DiverseResource> collection) {
        DiverseResources resources = new DiverseResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(DiverseResource diverse) {
        return getAllSelfHrefs(diverse).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(DiverseResource diverse) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(diverse.getSystemId()) && !isEmpty(diverse.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(diverse.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(DiverseResource diverse) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(diverse.getSystemId()) && !isEmpty(diverse.getSystemId().getIdentifikatorverdi())) {
            builder.add(diverse.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


package no.fint.consumer.models.uketimetall;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.UketimetallResource;
import no.fint.model.resource.administrasjon.kodeverk.UketimetallResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class UketimetallLinker extends FintLinker<UketimetallResource> {

    public UketimetallLinker() {
        super(UketimetallResource.class);
    }

    public void mapLinks(UketimetallResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public UketimetallResources toResources(Collection<UketimetallResource> collection) {
        UketimetallResources resources = new UketimetallResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(UketimetallResource uketimetall) {
        return getAllSelfHrefs(uketimetall).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(UketimetallResource uketimetall) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(uketimetall.getSystemId()) && !isEmpty(uketimetall.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(uketimetall.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(UketimetallResource uketimetall) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(uketimetall.getSystemId()) && !isEmpty(uketimetall.getSystemId().getIdentifikatorverdi())) {
            builder.add(uketimetall.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


package no.fint.consumer.models.anlegg;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.AnleggResource;
import no.fint.model.resource.administrasjon.kodeverk.AnleggResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class AnleggLinker extends FintLinker<AnleggResource> {

    public AnleggLinker() {
        super(AnleggResource.class);
    }

    public void mapLinks(AnleggResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public AnleggResources toResources(Collection<AnleggResource> collection) {
        AnleggResources resources = new AnleggResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(AnleggResource anlegg) {
        return getAllSelfHrefs(anlegg).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(AnleggResource anlegg) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(anlegg.getSystemId()) && !isEmpty(anlegg.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(anlegg.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(AnleggResource anlegg) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(anlegg.getSystemId()) && !isEmpty(anlegg.getSystemId().getIdentifikatorverdi())) {
            builder.add(anlegg.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


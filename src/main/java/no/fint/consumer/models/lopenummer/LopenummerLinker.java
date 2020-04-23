package no.fint.consumer.models.lopenummer;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.LopenummerResource;
import no.fint.model.resource.administrasjon.kodeverk.LopenummerResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class LopenummerLinker extends FintLinker<LopenummerResource> {

    public LopenummerLinker() {
        super(LopenummerResource.class);
    }

    public void mapLinks(LopenummerResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public LopenummerResources toResources(Collection<LopenummerResource> collection) {
        LopenummerResources resources = new LopenummerResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(LopenummerResource lopenummer) {
        return getAllSelfHrefs(lopenummer).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(LopenummerResource lopenummer) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(lopenummer.getSystemId()) && !isEmpty(lopenummer.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(lopenummer.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(LopenummerResource lopenummer) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(lopenummer.getSystemId()) && !isEmpty(lopenummer.getSystemId().getIdentifikatorverdi())) {
            builder.add(lopenummer.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


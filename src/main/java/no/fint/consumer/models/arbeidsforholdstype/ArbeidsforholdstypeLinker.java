package no.fint.consumer.models.arbeidsforholdstype;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.ArbeidsforholdstypeResource;
import no.fint.model.resource.administrasjon.kodeverk.ArbeidsforholdstypeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class ArbeidsforholdstypeLinker extends FintLinker<ArbeidsforholdstypeResource> {

    public ArbeidsforholdstypeLinker() {
        super(ArbeidsforholdstypeResource.class);
    }

    public void mapLinks(ArbeidsforholdstypeResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public ArbeidsforholdstypeResources toResources(Collection<ArbeidsforholdstypeResource> collection) {
        ArbeidsforholdstypeResources resources = new ArbeidsforholdstypeResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(ArbeidsforholdstypeResource arbeidsforholdstype) {
        return getAllSelfHrefs(arbeidsforholdstype).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(ArbeidsforholdstypeResource arbeidsforholdstype) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(arbeidsforholdstype.getSystemId()) && !isEmpty(arbeidsforholdstype.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(arbeidsforholdstype.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(ArbeidsforholdstypeResource arbeidsforholdstype) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(arbeidsforholdstype.getSystemId()) && !isEmpty(arbeidsforholdstype.getSystemId().getIdentifikatorverdi())) {
            builder.add(arbeidsforholdstype.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


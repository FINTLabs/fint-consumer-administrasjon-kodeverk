package no.fint.consumer.models.objekt;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.kodeverk.ObjektResource;
import no.fint.model.resource.administrasjon.kodeverk.ObjektResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class ObjektLinker extends FintLinker<ObjektResource> {

    public ObjektLinker() {
        super(ObjektResource.class);
    }

    public void mapLinks(ObjektResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public ObjektResources toResources(Collection<ObjektResource> collection) {
        ObjektResources resources = new ObjektResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(ObjektResource objekt) {
        return getAllSelfHrefs(objekt).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(ObjektResource objekt) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(objekt.getSystemId()) && !isEmpty(objekt.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(objekt.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(ObjektResource objekt) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(objekt.getSystemId()) && !isEmpty(objekt.getSystemId().getIdentifikatorverdi())) {
            builder.add(objekt.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}


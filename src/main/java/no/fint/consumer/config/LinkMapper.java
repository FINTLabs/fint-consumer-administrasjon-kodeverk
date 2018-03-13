package no.fint.consumer.config;

import no.fint.consumer.utils.RestEndpoints;
import java.util.Map;
import com.google.common.collect.ImmutableMap;
import no.fint.model.administrasjon.kodeverk.*;

public class LinkMapper {

	public static Map<String, String> linkMapper(String contextPath) {
		return ImmutableMap.<String,String>builder()
			.put(Ansvar.class.getName(), contextPath + RestEndpoints.ANSVAR)
			.put(Arbeidsforholdstype.class.getName(), contextPath + RestEndpoints.ARBEIDSFORHOLDSTYPE)
			.put(Art.class.getName(), contextPath + RestEndpoints.ART)
			.put(Fravarsgrunn.class.getName(), contextPath + RestEndpoints.FRAVARSGRUNN)
			.put(Fravarstype.class.getName(), contextPath + RestEndpoints.FRAVARSTYPE)
			.put(Funksjon.class.getName(), contextPath + RestEndpoints.FUNKSJON)
			.put(Lonnsart.class.getName(), contextPath + RestEndpoints.LONNSART)
			.put(Personalressurskategori.class.getName(), contextPath + RestEndpoints.PERSONALRESSURSKATEGORI)
			.put(Prosjekt.class.getName(), contextPath + RestEndpoints.PROSJEKT)
			.put(Stillingskode.class.getName(), contextPath + RestEndpoints.STILLINGSKODE)
			.put(Uketimetall.class.getName(), contextPath + RestEndpoints.UKETIMETALL)
			/* .put(TODO,TODO) */
			.build();
	}

}

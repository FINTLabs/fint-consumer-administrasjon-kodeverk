package no.fint.consumer.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ConsumerProps {


    public static final String CACHE_INITIALDELAY_ANSVAR = "${fint.consumer.cache.initialDelay.ansvar:60000}";
    public static final String CACHE_FIXEDRATE_ANSVAR = "${fint.consumer.cache.fixedRate.ansvar:900000}";

    public static final String CACHE_INITIALDELAY_ARBEIDFORHOLDSTYPE = "${fint.consumer.cache.initialDelay.arbeidsforholdstype:70000}";
    public static final String CACHE_FIXEDRATE_ARBEIDFORHOLDSTYPE = "${fint.consumer.cache.fixedRate.arbeidsforholdstype:900000}";

    public static final String CACHE_INITIALDELAY_ART = "${fint.consumer.cache.initialDelay.art:80000}";
    public static final String CACHE_FIXEDRATE_ART = "${fint.consumer.cache.fixedRate.art:900000}";

    public static final String CACHE_INITIALDELAY_FUNKSJON = "${fint.consumer.cache.initialDelay.funksjon:90000}";
    public static final String CACHE_FIXEDRATE_FUNKSJON = "${fint.consumer.cache.fixedRate.funksjon:900000}";

    public static final String CACHE_INITIALDELAY_PERSONALRESSURSKATEGORI = "${fint.consumer.cache.initialDelay.personalressurskategori:100000}";
    public static final String CACHE_FIXEDRATE_PERSONALRESSURSKATEGORI = "${fint.consumer.cache.fixedRate.personalressurskategori:900000}";

    public static final String CACHE_INITIALDELAY_PROSJEKT = "${fint.consumer.cache.initialDelay.prosjekt:110000}";
    public static final String CACHE_FIXEDRATE_PROSJEKT = "${fint.consumer.cache.fixedRate.prosjekt:900000}";

    public static final String CACHE_INITIALDELAY_STILLINGSKODE = "${fint.consumer.cache.initialDelay.stillingskode:120000}";
    public static final String CACHE_FIXEDRATE_STILLINGSKODE = "${fint.consumer.cache.fixedRate.stillingskode:900000}";

    public static final String CACHE_INITIALDELAY_UKETIMETALL = "${fint.consumer.cache.initialDelay.uketimetall:130000}";
    public static final String CACHE_FIXEDRATE_UKETIMETALL = "${fint.consumer.cache.fixedRate.uketimetall:900000}";

    public static final String CACHE_INITIALDELAY_LONNSART = "${fint.consumer.cache.initialDelay.lonnsart:140000}";
    public static final String CACHE_FIXEDRATE_LONNSART = "${fint.consumer.cache.fixedRate.lonnsart:900000}";


    @Value("${fint.events.orgIds:fint.no}")
    private String[] orgs;

}

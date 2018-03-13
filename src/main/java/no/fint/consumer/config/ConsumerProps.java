package no.fint.consumer.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ConsumerProps {
    
    @Value("${fint.consumer.override-org-id:false}")
    private boolean overrideOrgId;

    @Value("${fint.consumer.default-client:FINT}")
    private String defaultClient;

    @Value("${fint.consumer.default-org-id:fint.no}")
    private String defaultOrgId;
    
    @Value("${fint.events.orgIds:fint.no}")
    private String[] orgs;

    
    public static final String CACHE_INITIALDELAY_ANSVAR = "${fint.consumer.cache.initialDelay.ansvar:60000}";
    public static final String CACHE_FIXEDRATE_ANSVAR = "${fint.consumer.cache.fixedRate.ansvar:900000}";
    
    public static final String CACHE_INITIALDELAY_ARBEIDSFORHOLDSTYPE = "${fint.consumer.cache.initialDelay.arbeidsforholdstype:70000}";
    public static final String CACHE_FIXEDRATE_ARBEIDSFORHOLDSTYPE = "${fint.consumer.cache.fixedRate.arbeidsforholdstype:900000}";
    
    public static final String CACHE_INITIALDELAY_ART = "${fint.consumer.cache.initialDelay.art:80000}";
    public static final String CACHE_FIXEDRATE_ART = "${fint.consumer.cache.fixedRate.art:900000}";
    
    public static final String CACHE_INITIALDELAY_FRAVARSGRUNN = "${fint.consumer.cache.initialDelay.fravarsgrunn:90000}";
    public static final String CACHE_FIXEDRATE_FRAVARSGRUNN = "${fint.consumer.cache.fixedRate.fravarsgrunn:900000}";
    
    public static final String CACHE_INITIALDELAY_FRAVARSTYPE = "${fint.consumer.cache.initialDelay.fravarstype:100000}";
    public static final String CACHE_FIXEDRATE_FRAVARSTYPE = "${fint.consumer.cache.fixedRate.fravarstype:900000}";
    
    public static final String CACHE_INITIALDELAY_FUNKSJON = "${fint.consumer.cache.initialDelay.funksjon:110000}";
    public static final String CACHE_FIXEDRATE_FUNKSJON = "${fint.consumer.cache.fixedRate.funksjon:900000}";
    
    public static final String CACHE_INITIALDELAY_LONNSART = "${fint.consumer.cache.initialDelay.lonnsart:120000}";
    public static final String CACHE_FIXEDRATE_LONNSART = "${fint.consumer.cache.fixedRate.lonnsart:900000}";
    
    public static final String CACHE_INITIALDELAY_PERSONALRESSURSKATEGORI = "${fint.consumer.cache.initialDelay.personalressurskategori:130000}";
    public static final String CACHE_FIXEDRATE_PERSONALRESSURSKATEGORI = "${fint.consumer.cache.fixedRate.personalressurskategori:900000}";
    
    public static final String CACHE_INITIALDELAY_PROSJEKT = "${fint.consumer.cache.initialDelay.prosjekt:140000}";
    public static final String CACHE_FIXEDRATE_PROSJEKT = "${fint.consumer.cache.fixedRate.prosjekt:900000}";
    
    public static final String CACHE_INITIALDELAY_STILLINGSKODE = "${fint.consumer.cache.initialDelay.stillingskode:150000}";
    public static final String CACHE_FIXEDRATE_STILLINGSKODE = "${fint.consumer.cache.fixedRate.stillingskode:900000}";
    
    public static final String CACHE_INITIALDELAY_UKETIMETALL = "${fint.consumer.cache.initialDelay.uketimetall:160000}";
    public static final String CACHE_FIXEDRATE_UKETIMETALL = "${fint.consumer.cache.fixedRate.uketimetall:900000}";
    

}

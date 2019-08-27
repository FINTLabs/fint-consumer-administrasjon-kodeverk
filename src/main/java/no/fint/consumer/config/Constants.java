
package no.fint.consumer.config;

public enum Constants {
;

    public static final String COMPONENT = "administrasjon-kodeverk";
    public static final String COMPONENT_CONSUMER = COMPONENT + " consumer";
    public static final String CACHE_SERVICE = "CACHE_SERVICE";

    
    public static final String CACHE_INITIALDELAY_ANSVAR = "${fint.consumer.cache.initialDelay.ansvar:900000}";
    public static final String CACHE_FIXEDRATE_ANSVAR = "${fint.consumer.cache.fixedRate.ansvar:900000}";
    
    public static final String CACHE_INITIALDELAY_ARBEIDSFORHOLDSTYPE = "${fint.consumer.cache.initialDelay.arbeidsforholdstype:910000}";
    public static final String CACHE_FIXEDRATE_ARBEIDSFORHOLDSTYPE = "${fint.consumer.cache.fixedRate.arbeidsforholdstype:900000}";
    
    public static final String CACHE_INITIALDELAY_ART = "${fint.consumer.cache.initialDelay.art:920000}";
    public static final String CACHE_FIXEDRATE_ART = "${fint.consumer.cache.fixedRate.art:900000}";
    
    public static final String CACHE_INITIALDELAY_FRAVARSGRUNN = "${fint.consumer.cache.initialDelay.fravarsgrunn:930000}";
    public static final String CACHE_FIXEDRATE_FRAVARSGRUNN = "${fint.consumer.cache.fixedRate.fravarsgrunn:900000}";
    
    public static final String CACHE_INITIALDELAY_FRAVARSTYPE = "${fint.consumer.cache.initialDelay.fravarstype:940000}";
    public static final String CACHE_FIXEDRATE_FRAVARSTYPE = "${fint.consumer.cache.fixedRate.fravarstype:900000}";
    
    public static final String CACHE_INITIALDELAY_FUNKSJON = "${fint.consumer.cache.initialDelay.funksjon:950000}";
    public static final String CACHE_FIXEDRATE_FUNKSJON = "${fint.consumer.cache.fixedRate.funksjon:900000}";
    
    public static final String CACHE_INITIALDELAY_LONNSART = "${fint.consumer.cache.initialDelay.lonnsart:960000}";
    public static final String CACHE_FIXEDRATE_LONNSART = "${fint.consumer.cache.fixedRate.lonnsart:900000}";
    
    public static final String CACHE_INITIALDELAY_PERSONALRESSURSKATEGORI = "${fint.consumer.cache.initialDelay.personalressurskategori:970000}";
    public static final String CACHE_FIXEDRATE_PERSONALRESSURSKATEGORI = "${fint.consumer.cache.fixedRate.personalressurskategori:900000}";
    
    public static final String CACHE_INITIALDELAY_PROSJEKT = "${fint.consumer.cache.initialDelay.prosjekt:980000}";
    public static final String CACHE_FIXEDRATE_PROSJEKT = "${fint.consumer.cache.fixedRate.prosjekt:900000}";
    
    public static final String CACHE_INITIALDELAY_STILLINGSKODE = "${fint.consumer.cache.initialDelay.stillingskode:990000}";
    public static final String CACHE_FIXEDRATE_STILLINGSKODE = "${fint.consumer.cache.fixedRate.stillingskode:900000}";
    
    public static final String CACHE_INITIALDELAY_UKETIMETALL = "${fint.consumer.cache.initialDelay.uketimetall:1000000}";
    public static final String CACHE_FIXEDRATE_UKETIMETALL = "${fint.consumer.cache.fixedRate.uketimetall:900000}";
    

}

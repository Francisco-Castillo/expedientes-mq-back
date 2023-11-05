package ar.com.mq.expedientes.api.enums;

public enum TemplateEnum {

    CARATULA ("CARATULA"),
    ORDENPAGO ("ORDEN DE PAGO");

    private String value;

    TemplateEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package ar.com.mq.expedientes.api.enums;

public enum ParametroEnum {

    DIRECTORIO_DE_ALMACENAMIENTO("DIRECTORIO_DE_ALMACENAMIENTO"),
    ULTIMO_NUMERO_DE_EXPEDIENTE("ULTIMO_NUMERO_DE_EXPEDIENTE");

    private String value;

    ParametroEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

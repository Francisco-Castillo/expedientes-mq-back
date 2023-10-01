package ar.com.mq.expedientes.api.enums;

import lombok.Getter;

@Getter
public enum EstadoExpedienteEnum {

    INICIADO("Iniciado"),
    TRAMITACION("Tramitación"),
    SUBSANACION("Subsanación"),
    RESOLUCION("En resolución"),
    ARCHIVO("Archivado");

    private String value;

    EstadoExpedienteEnum(String value) {
        this.value = value;
    }


}

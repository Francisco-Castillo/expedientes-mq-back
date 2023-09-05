package ar.com.mq.expedientes.api.enums;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;

public enum TipoExpedienteEnum {
    COMPRA,
    PAGO,
    PERSONAL,
    SERVICIO,
    SUBSIDIO;

    public static boolean in (String tipoExpediente, List<TipoExpedienteEnum> entidades){
        //return CollectionUtils.isNotEmpty(entidades) && entidades.stream().anyMatch(entidad -> is(e))
        return true;
    }

    public static boolean is (String entidad, TipoExpedienteEnum tipoExpedienteEnum){
        return Objects.equals(tipoExpedienteEnum.name(), entidad);
    }
}

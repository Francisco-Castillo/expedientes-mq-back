package ar.com.mq.expedientes.api.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ExpedientePagoDTO extends ExpedienteDTO{

    @Builder(builderMethodName = "expedientePagoDTOBuilder")

    public ExpedientePagoDTO(Long id, String numero, String referencia, LocalDateTime fechaCaratulacion, String descripcion, String codigoTramite, String tipo, int cantidadFojas, BigDecimal monto, String estado, LocalDateTime ultimaActualizacion) {
        super(id, numero, referencia, fechaCaratulacion, descripcion, codigoTramite, tipo, cantidadFojas, monto, estado, ultimaActualizacion);
    }
}

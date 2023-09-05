package ar.com.mq.expedientes.api.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ExpedientePagoDTO extends ExpedienteDTO{

    @Builder(builderMethodName = "expedientePagoDTOBuilder")
    public ExpedientePagoDTO(Long id, String numero, String referencia, LocalDateTime fechaCaratulacion, String descripcion, String codigoTramite, String tipo) {
        super(id, numero, referencia, fechaCaratulacion, descripcion, codigoTramite, tipo);
    }
}

package ar.com.mq.expedientes.api.model.entity;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue(value = "SERVICIO")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExpedienteServicio extends Expediente{

    @Builder(builderMethodName = "expedienteServicioBuilder")
    public ExpedienteServicio(Long id, String numero, String referencia, LocalDateTime fechaCaratulacion, String descripcion, String codigoTramite, String tipo) {
        super(id, numero, referencia, fechaCaratulacion, descripcion, codigoTramite, tipo);
    }
}

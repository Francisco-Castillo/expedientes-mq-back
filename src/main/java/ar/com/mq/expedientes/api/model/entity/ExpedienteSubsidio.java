package ar.com.mq.expedientes.api.model.entity;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue(value = "SUBSIDIO")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExpedienteSubsidio extends Expediente{

    @Builder(builderMethodName = "expedienteSubsidioBuilder")
    public ExpedienteSubsidio(Long id, String numero, String referencia, LocalDateTime fechaCaratulacion, String descripcion, String codigoTramite, String tipo) {
        super(id, numero, referencia, fechaCaratulacion, descripcion, codigoTramite, tipo);
    }
}

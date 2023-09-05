package ar.com.mq.expedientes.api.model.entity;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue(value = "PAGO")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExpedientePago extends Expediente {

    @Builder(builderMethodName = "expedientePagoBuilder")
    public ExpedientePago(Long id, String numero, String referencia, LocalDateTime fechaCaratulacion, String descripcion, String codigoTramite, String tipo) {
        super(id, numero, referencia, fechaCaratulacion, descripcion, codigoTramite, tipo);
    }
}

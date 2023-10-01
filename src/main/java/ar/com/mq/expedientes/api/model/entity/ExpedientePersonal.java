package ar.com.mq.expedientes.api.model.entity;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue(value = "PERSONAL")

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExpedientePersonal extends Expediente{

    @Builder(builderMethodName = "expedientePersonalBuilder")

    public ExpedientePersonal(Long id, String numero, String referencia, LocalDateTime fechaCaratulacion, String descripcion, String codigoTramite, String tipo, String estado, Integer cantidadFojas, BigDecimal monto) {
        super(id, numero, referencia, fechaCaratulacion, descripcion, codigoTramite, tipo, estado, cantidadFojas, monto);
    }
}

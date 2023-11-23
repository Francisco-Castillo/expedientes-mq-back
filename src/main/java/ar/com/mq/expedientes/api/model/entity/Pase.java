package ar.com.mq.expedientes.api.model.entity;

import ar.com.mq.expedientes.core.business.bean.MunicipalidadMQEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Pase")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Builder
@ToString
public class Pase extends MunicipalidadMQEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "usuario_emisor_id")
    private Usuario usuarioEmisor;

    @ManyToOne
    @JoinColumn(name = "usuario_receptor_id")
    private Usuario usuarioReceptor;

    @ManyToOne
    @JoinColumn(name = "expediente_id")
    private Expediente expediente;

    @Column(name = "observaciones")
    private String observaciones;

    @Override
    public Serializable getPrimaryKey() {
        return null;
    }
}

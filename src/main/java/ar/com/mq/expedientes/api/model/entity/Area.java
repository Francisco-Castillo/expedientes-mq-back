package ar.com.mq.expedientes.api.model.entity;

import ar.com.mq.expedientes.core.business.bean.MunicipalidadMQEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Area")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Builder
public class Area extends MunicipalidadMQEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "referencia_id")
    private Long referenciaId;

    @Column(name = "nivel")
    private Integer nivel;

    @Column(name = "codigo_presupuestario")
    private String codigoPresupuestario;

    @Override
    public Serializable getPrimaryKey() {
        return this.id;
    }
}

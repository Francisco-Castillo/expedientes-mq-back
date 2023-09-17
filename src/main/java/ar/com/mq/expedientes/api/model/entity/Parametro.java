package ar.com.mq.expedientes.api.model.entity;

import ar.com.mq.expedientes.core.business.bean.MunicipalidadMQEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Parametro")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Parametro extends MunicipalidadMQEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String nombre;

    private String valor;

    private String descripcion;

    @Override
    public Serializable getPrimaryKey() {
        return id;
    }

}

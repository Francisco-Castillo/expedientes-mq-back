package ar.com.mq.expedientes.api.model.entity;

import ar.com.mq.expedientes.core.business.bean.MunicipalidadMQEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Documento")
@Data
@Builder
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Documento extends MunicipalidadMQEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha_subida")
    private LocalDateTime fechaSubida;

    private String ubicacion;

    private String observaciones;

    @JoinColumn(name = "tipo_documento_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoDocumento tipoDocumentoId;

    @JoinColumn(name = "expediente_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Expediente expediente;

    @Override
    public Serializable getPrimaryKey() {
        return id;
    }
}

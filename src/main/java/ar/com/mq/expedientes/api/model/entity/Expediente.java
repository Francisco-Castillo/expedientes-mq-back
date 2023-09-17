package ar.com.mq.expedientes.api.model.entity;

import ar.com.mq.expedientes.core.business.bean.MunicipalidadMQEntity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Expediente")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString
@Builder
public class Expediente extends MunicipalidadMQEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String numero;

    private String referencia;

    @Column(name = "fecha_caratulacion")
    private LocalDateTime fechaCaratulacion;

    private String descripcion;

    @Column(name = "codigo_tramite")
    private String codigoTramite;

    @Column(insertable = false, updatable = false)
    private String tipo;

    @OneToMany(mappedBy = "expediente", fetch = FetchType.LAZY)
    private List<Documento> documentos;

    public Expediente(Long id, String numero, String referencia, LocalDateTime fechaCaratulacion, String descripcion, String codigoTramite, String tipo) {
        this.id = id;
        this.numero = numero;
        this.referencia = referencia;
        this.fechaCaratulacion = fechaCaratulacion;
        this.descripcion = descripcion;
        this.codigoTramite = codigoTramite;
        this.tipo = tipo;
    }

    @Override
    public Serializable getPrimaryKey() {
        return id;
    }

}

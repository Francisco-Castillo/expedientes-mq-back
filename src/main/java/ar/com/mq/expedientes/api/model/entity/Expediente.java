package ar.com.mq.expedientes.api.model.entity;

import ar.com.mq.expedientes.core.business.bean.MunicipalidadMQEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "cantidad_fojas")
    private int cantidadFojas;

    @Column(name = "monto")
    private BigDecimal monto;

    @Column(insertable = false, updatable = false)
    private String tipo;

    @Column(name = "estado")
    private String estado;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    @OneToMany(mappedBy = "expediente", fetch = FetchType.LAZY)
    private List<Documento> documentos;

    public Expediente(Long id, String numero, String referencia, LocalDateTime fechaCaratulacion, String descripcion,
                      String codigoTramite, String tipo, String estado, Integer cantidadFojas, BigDecimal monto) {
        this.id = id;
        this.numero = numero;
        this.referencia = referencia;
        this.fechaCaratulacion = fechaCaratulacion;
        this.descripcion = descripcion;
        this.codigoTramite = codigoTramite;
        this.tipo = tipo;
        this.cantidadFojas = cantidadFojas;
        this.monto = monto;
        this.estado = estado;
    }

    @Override
    public Serializable getPrimaryKey() {
        return id;
    }

}

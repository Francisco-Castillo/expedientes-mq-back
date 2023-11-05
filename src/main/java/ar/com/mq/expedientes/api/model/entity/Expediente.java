package ar.com.mq.expedientes.api.model.entity;

import ar.com.mq.expedientes.core.business.bean.MunicipalidadMQEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Expediente")
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

    private String iniciador;

    private String numero;

    private String referencia;

    @Column(name = "fecha_caratulacion")
    private LocalDate fechaCaratulacion;

    private String descripcion;

    @Column(name = "codigo_tramite")
    private String codigoTramite;

    @Column(name = "cantidad_fojas")
    private int cantidadFojas;

    @Column(name = "monto")
    private BigDecimal monto;

    private String tipo;

    @Column(name = "estado")
    private String estado;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "expediente", orphanRemoval = true)
    private List<Documento> documentos;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "expediente", orphanRemoval = true)
    private List<Pase> pases;

    @ManyToOne
    @JoinColumn(name = "usuariocaratulador_id")
    private Usuario usuario;

    @Override
    public Serializable getPrimaryKey() {
        return id;
    }

}

package ar.com.mq.expedientes.api.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ar.com.mq.expedientes.core.business.bean.MunicipalidadMQEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Expediente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" }, callSuper = false)
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

	private String responsable;

	@Override
	public Serializable getPrimaryKey() {
		return id;
	}

}

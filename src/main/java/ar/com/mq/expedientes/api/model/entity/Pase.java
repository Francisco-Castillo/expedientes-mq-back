package ar.com.mq.expedientes.api.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.com.mq.expedientes.core.business.bean.MunicipalidadMQEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Pase")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" }, callSuper = false)
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

	@Column(name = "en_bandeja")
	private Integer enBandeja;

	@Column(name = "observaciones")
	private String observaciones;

	@Override
	public Serializable getPrimaryKey() {
		return null;
	}
}

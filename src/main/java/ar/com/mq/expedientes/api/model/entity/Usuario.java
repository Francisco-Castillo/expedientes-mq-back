package ar.com.mq.expedientes.api.model.entity;

import java.io.Serializable;
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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Usuario")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends MunicipalidadMQEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	private String nombre;

	private String apellido;

	private String dni;

	@Column(name = "email", nullable = false, unique = true, length = 100)
	private String email;

	@Column(name = "password", nullable = false, length = 400)
	private String password;

	@Column(name = "uuid")
	private String uuid;

	@Column(name = "fecha_alta")
	private LocalDateTime fechaAlta;

	private Integer estado;

	private Integer primerLogin;

	@Column(name = "fecha_baja")
	private String fechaBaja;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "area_id")
	private Area areaId;

	@OneToMany(mappedBy = "usuarioEmisor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Pase> pasesEnviados;

	@OneToMany(mappedBy = "usuarioReceptor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Pase> pasesRecibidos;

	@Override
	public Serializable getPrimaryKey() {
		return this.id;
	}
}

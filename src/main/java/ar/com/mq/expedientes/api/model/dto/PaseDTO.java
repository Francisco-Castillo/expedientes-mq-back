package ar.com.mq.expedientes.api.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PaseDTO {
	
	private Long id;
	private LocalDateTime fechaHora;
	private String observaciones;
	private Long expedienteId;
	private Long usuarioEmisorId;
	private Long usuarioReceptorId;

}

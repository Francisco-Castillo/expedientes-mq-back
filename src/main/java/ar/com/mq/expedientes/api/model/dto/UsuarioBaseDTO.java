package ar.com.mq.expedientes.api.model.dto;

import ar.com.mq.expedientes.core.business.bean.MunicipalidadMQDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioBaseDTO extends MunicipalidadMQDTO {
	
	private Long id;

    private String nombre;

    private String apellido;

    private String documento;

    private Integer estado;

    private Integer primerLogin; //0 - Si 1- NO

    private String email;

    private AreaDTO area;
}

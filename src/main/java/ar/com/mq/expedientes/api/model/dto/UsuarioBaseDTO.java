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

    private String email;
    private AreaDTO area;
}

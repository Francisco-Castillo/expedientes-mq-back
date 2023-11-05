package ar.com.mq.expedientes.api.model.dto;

import ar.com.mq.expedientes.core.business.bean.MunicipalidadMQDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ExpedienteDTO extends MunicipalidadMQDTO {

    private Long id;

    private String iniciador; // Entidad que inicia el expediente. No es lo mismo que el caratulador.

    private String numero;

    private String referencia;

    private LocalDate fechaCaratulacion;

    private String descripcion;

    private String codigoTramite;

    private String tipo;

    private int cantidadFojas;

    private BigDecimal monto;

    private String estado;

    private LocalDateTime ultimaActualizacion;

    private List<DocumentoDTO> documentos;

    private UsuarioDTO usuario;
}

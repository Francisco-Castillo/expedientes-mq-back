package ar.com.mq.expedientes.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentoDTO {

    private Long id;

    private LocalDateTime fechaSubida;

    private String ubicacion;

    private String observaciones;

    private Long tipoDocumentoId;

    private Long expedienteId;
}

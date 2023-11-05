package ar.com.mq.expedientes.api.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateDTO {
    private Long id;
    private String content;

    private String type;
}

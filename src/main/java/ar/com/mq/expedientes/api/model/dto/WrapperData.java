package ar.com.mq.expedientes.api.model.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WrapperData <T>{

    private Integer totalPages;
    private Integer currentPage;
    private Long totalItems;
    private List<T> items;
}

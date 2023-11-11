package ar.com.mq.expedientes.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDataDTO {
	
	private Long userId;
	private String email;
	private String name;
	private String lastName;
	private Long areaId;
	private String areaName;

}

package ar.com.mq.expedientes.core.exception.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorHTTPDTO {

    private HttpStatus httpStatus;
    private String errorCode;
    private List<String> messages = new ArrayList<>();

    public ErrorHTTPDTO(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ErrorHTTPDTO(HttpStatus httpStatus, String errorCode) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}

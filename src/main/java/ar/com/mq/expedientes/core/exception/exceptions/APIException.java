package ar.com.mq.expedientes.core.exception.exceptions;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class APIException extends RuntimeException{

    private final HttpStatus httpStatus;
    private final String errorCode;

    public APIException(String message, Throwable cause, HttpStatus httpStatus, String errorCode) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public APIException(HttpStatus httpStatus, String errorCode, String message){
        this(message, null, httpStatus, errorCode);
    }


}

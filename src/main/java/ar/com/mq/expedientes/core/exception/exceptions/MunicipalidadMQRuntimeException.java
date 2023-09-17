package ar.com.mq.expedientes.core.exception.exceptions;

import org.springframework.http.HttpStatus;

public class MunicipalidadMQRuntimeException extends APIException {

    public MunicipalidadMQRuntimeException(HttpStatus httpStatus, String message){
        super(httpStatus,"",message);
    }

    public MunicipalidadMQRuntimeException(HttpStatus httpStatus, String errorCode, String message) {
        super(httpStatus, errorCode, message);
    }

    public static MunicipalidadMQRuntimeException notFoundException(String message){
        return new MunicipalidadMQRuntimeException(HttpStatus.NOT_FOUND, message);
    }

    public static MunicipalidadMQRuntimeException conflictException(String message){
        return new MunicipalidadMQRuntimeException(HttpStatus.CONFLICT, message);
    }

    public static MunicipalidadMQRuntimeException badRequestException(String message){
        return new MunicipalidadMQRuntimeException(HttpStatus.BAD_REQUEST, message);
    }
}

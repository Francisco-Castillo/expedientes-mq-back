package ar.com.mq.expedientes.core.exception.handler;

import ar.com.mq.expedientes.core.exception.bean.ErrorHTTPDTO;
import ar.com.mq.expedientes.core.exception.exceptions.MunicipalidadMQRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // Request.
    @ExceptionHandler(value = HttpMediaTypeException.class)
    public ResponseEntity<Object> handleException(final HttpMediaTypeException e){
        log.error("Error tecnico: {}",e.getMessage());

        ErrorHTTPDTO error =  ErrorHTTPDTO.builder()
                .httpStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .errorCode(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                .messages(List.of(e.getMessage()))
                .build();

        return new ResponseEntity<>(error, error.getHttpStatus());
    }


    @ExceptionHandler(value = MunicipalidadMQRuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(final MunicipalidadMQRuntimeException e){
        log.error("Error de negocio: {}",e.getMessage());

        ErrorHTTPDTO error =  ErrorHTTPDTO.builder()
                .httpStatus(e.getHttpStatus())
                .errorCode(e.getErrorCode())
                .messages(List.of(e.getMessage()))
                .build();

        return new ResponseEntity<>(error, error.getHttpStatus());
    }
}

package ar.com.mq.expedientes.core.business;

import java.util.List;

public interface MunicipalidadMQMapper<E, T>{
    E toEntity(T dto);

    T toDTO(E entity);

    List<T> toListDTO(List<E> entities);
}

package ar.com.mq.expedientes.api.service.repository;

import ar.com.mq.expedientes.api.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

    Optional<Usuario> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Usuario u SET u.password =:password, u.primerLogin=1 WHERE u.id =:userId")
    void changePassword(Long userId, String password);

    @Transactional
    @Modifying
    @Query("UPDATE Usuario u SET u.estado=:status WHERE u.id =:userId")
    void changeStatus(Long userId, Integer status);
}

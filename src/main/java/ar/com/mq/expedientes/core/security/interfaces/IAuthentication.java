package ar.com.mq.expedientes.core.security.interfaces;

import java.security.Principal;
import org.springframework.security.core.Authentication;

public interface IAuthentication {

    Authentication getAuthentication();

    Principal getPrincipal();

}

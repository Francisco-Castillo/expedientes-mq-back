package ar.com.mq.expedientes.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.ZoneId;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ZonedUtils {

    public static final String AMERICA_BUENOS_AIRES = "America/Argentina/Buenos_Aires";

    public static final ZoneId ARGENTINA() {
        return ZoneId.of(AMERICA_BUENOS_AIRES);
    }
}

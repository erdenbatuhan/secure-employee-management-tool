package com.d8games.web.services.exception;

import com.d8games.web.services.config.ConfigManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NightHoursException extends RuntimeException {

    public NightHoursException() {
        super(String.format("You cannot vouch after %s and before %s!",
                ConfigManager.getNightHoursStart(), ConfigManager.getNightHoursEnd()));
    }
}

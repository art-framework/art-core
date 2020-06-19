package net.silthus.art.api.config;

import net.silthus.art.api.ArtException;

public class ArtConfigException extends ArtException {

    public ArtConfigException() {
    }

    public ArtConfigException(String message) {
        super(message);
    }

    public ArtConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArtConfigException(Throwable cause) {
        super(cause);
    }

    public ArtConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

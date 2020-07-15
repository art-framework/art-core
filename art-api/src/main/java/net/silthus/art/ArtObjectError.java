package net.silthus.art;

import lombok.Getter;

import javax.annotation.concurrent.Immutable;
import java.net.URL;

/**
 * Container that wraps errors that can occur when finding and
 * register {@link ArtObject}s with the {@link ArtFinder}.
 */
@Getter
@Immutable
public class ArtObjectError {

    public static ArtObjectError of(String message, Reason reason, Class<? extends ArtObject> artObject) {
        return new ArtObjectError(message, reason, artObject);
    }

    /**
     * A descriptive message why this {@link ArtObject} cannot be
     * registered.
     */
    private final String message;
    /**
     * The error code or reason for the error.
     */
    private final Reason reason;
    /**
     * The actual class of the {@link ArtObject} that produced the error.
     */
    private final Class<? extends ArtObject> artObject;
    /**
     * Returns the source file of the underlying {@link ArtObject}.
     * This is either a class file in a path or a JAR file containing the class.
     */
    private final URL location;

    ArtObjectError(String message, Reason reason, Class<? extends ArtObject> artObject) {
        this.message = message;
        this.reason = reason;
        this.artObject = artObject;
        this.location = artObject.getProtectionDomain().getCodeSource().getLocation();
    }

    enum Reason {
        UNKNOWN,
        NO_IDENTIFIER,
        INVALID_CONSTRUCTOR,
        NO_ANNOTATION,
        INVALID_CONFIG;
    }
}

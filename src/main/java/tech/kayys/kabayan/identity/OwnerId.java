package tech.kayys.kabayan.identity;

import java.util.Objects;

/** Opaque, stable identifier for the authenticated person who owns a personal space. */
public record OwnerId(String value) {
    public OwnerId {
        Objects.requireNonNull(value, "value");
        if (value.isBlank() || !value.equals(value.trim())) {
            throw new IllegalArgumentException("OwnerId must be non-blank and have no surrounding whitespace");
        }
    }
}

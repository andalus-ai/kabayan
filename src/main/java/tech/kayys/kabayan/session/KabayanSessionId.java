package tech.kayys.kabayan.session;

import java.util.Objects;
import java.util.UUID;

/** Kabayan-owned conversation/session identity; never an Andalus session or run ID. */
public record KabayanSessionId(String value) {
    public KabayanSessionId {
        Objects.requireNonNull(value, "value");
        if (value.isBlank() || !value.equals(value.trim())) {
            throw new IllegalArgumentException("KabayanSessionId must be non-blank and have no surrounding whitespace");
        }
    }

    public static KabayanSessionId create() {
        return new KabayanSessionId(UUID.randomUUID().toString());
    }
}

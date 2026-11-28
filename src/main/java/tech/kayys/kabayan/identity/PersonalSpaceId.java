package tech.kayys.kabayan.identity;

import java.util.Objects;

/** Opaque identifier for an isolated personal-data boundary. */
public record PersonalSpaceId(String value) {
    public PersonalSpaceId {
        Objects.requireNonNull(value, "value");
        if (value.isBlank() || !value.equals(value.trim())) {
            throw new IllegalArgumentException("PersonalSpaceId must be non-blank and have no surrounding whitespace");
        }
    }
}

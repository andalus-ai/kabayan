package tech.kayys.kabayan.session;

import java.util.Objects;

/** Namespaced external Runtime reference; resource IDs remain opaque to Kabayan. */
public record RuntimeResourceRef(
        String provider,
        RuntimeResourceKind kind,
        String resourceId,
        String environment) {

    public RuntimeResourceRef {
        requireToken(provider, "provider");
        Objects.requireNonNull(kind, "kind");
        requireToken(resourceId, "resourceId");
        requireToken(environment, "environment");
    }

    private static void requireToken(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank() || !value.equals(value.trim())) {
            throw new IllegalArgumentException(name + " must be non-blank and have no surrounding whitespace");
        }
    }
}

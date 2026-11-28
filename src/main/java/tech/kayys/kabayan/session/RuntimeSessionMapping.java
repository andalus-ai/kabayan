package tech.kayys.kabayan.session;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import tech.kayys.kabayan.identity.PersonalSpaceScope;

/**
 * Explicit mapping from a Kabayan-owned session to an external Runtime session.
 * The external session kind, provider and environment are retained to prevent
 * accidental equivalence between unrelated Runtime identifier namespaces.
 */
public record RuntimeSessionMapping(
        UUID mappingId,
        PersonalSpaceScope scope,
        KabayanSessionId kabayanSessionId,
        RuntimeResourceRef runtimeSession,
        Instant createdAt,
        Instant updatedAt,
        long revision) {

    public RuntimeSessionMapping {
        Objects.requireNonNull(mappingId, "mappingId");
        Objects.requireNonNull(scope, "scope");
        Objects.requireNonNull(kabayanSessionId, "kabayanSessionId");
        Objects.requireNonNull(runtimeSession, "runtimeSession");
        Objects.requireNonNull(createdAt, "createdAt");
        Objects.requireNonNull(updatedAt, "updatedAt");
        if (!runtimeSession.kind().isSession()) {
            throw new IllegalArgumentException("RuntimeSessionMapping requires a project or operator session reference");
        }
        if (updatedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("updatedAt must not be before createdAt");
        }
        if (revision < 0) {
            throw new IllegalArgumentException("revision must be non-negative");
        }
    }

    public static RuntimeSessionMapping create(
            PersonalSpaceScope scope,
            KabayanSessionId kabayanSessionId,
            RuntimeResourceRef runtimeSession,
            Instant now) {
        return new RuntimeSessionMapping(
                UUID.randomUUID(), scope, kabayanSessionId, runtimeSession, now, now, 0);
    }
}

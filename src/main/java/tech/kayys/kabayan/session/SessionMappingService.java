package tech.kayys.kabayan.session;

import java.time.Clock;
import java.util.Objects;
import tech.kayys.kabayan.identity.PersonalSpaceScope;

/** Application service for idempotent, owner-scoped session linking. */
public final class SessionMappingService {
    private final SessionMappingRepository repository;
    private final Clock clock;

    public SessionMappingService(SessionMappingRepository repository, Clock clock) {
        this.repository = Objects.requireNonNull(repository, "repository");
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    /**
     * Links a Kabayan session to an external Runtime session.
     *
     * <p>Repeating the same link is idempotent. Attempting to rebind the
     * Kabayan session to another external session is a conflict; callers must
     * create a new Kabayan session or perform an explicit future migration.</p>
     */
    public RuntimeSessionMapping link(
            PersonalSpaceScope scope,
            KabayanSessionId kabayanSessionId,
            RuntimeResourceRef runtimeSession) {
        Objects.requireNonNull(scope, "scope");
        Objects.requireNonNull(kabayanSessionId, "kabayanSessionId");
        Objects.requireNonNull(runtimeSession, "runtimeSession");

        if (!runtimeSession.kind().isSession()) {
            throw new IllegalArgumentException("Only Runtime session references can be linked to a Kabayan session");
        }

        return repository.find(scope, kabayanSessionId)
                .map(existing -> {
                    if (!existing.runtimeSession().equals(runtimeSession)) {
                        throw new SessionMappingConflictException();
                    }
                    return existing;
                })
                .orElseGet(() -> repository.createIfAbsent(
                        RuntimeSessionMapping.create(scope, kabayanSessionId, runtimeSession, clock.instant())));
    }
}

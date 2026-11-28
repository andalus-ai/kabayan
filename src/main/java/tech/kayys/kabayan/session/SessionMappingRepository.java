package tech.kayys.kabayan.session;

import java.util.Optional;
import tech.kayys.kabayan.identity.PersonalSpaceScope;

/**
 * Persistence port for explicit Kabayan-to-Andalus session identity mappings.
 *
 * <p>Implementations must scope every operation by both owner and personal
 * space. {@link #createIfAbsent(RuntimeSessionMapping)} must be atomic: when a
 * mapping already exists for the scoped Kabayan session, return the existing
 * mapping if its external reference matches, otherwise throw
 * {@link SessionMappingConflictException}.</p>
 */
public interface SessionMappingRepository {
    Optional<RuntimeSessionMapping> find(PersonalSpaceScope scope, KabayanSessionId sessionId);

    RuntimeSessionMapping createIfAbsent(RuntimeSessionMapping mapping);

    boolean delete(PersonalSpaceScope scope, KabayanSessionId sessionId);
}

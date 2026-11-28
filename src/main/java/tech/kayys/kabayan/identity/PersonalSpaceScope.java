package tech.kayys.kabayan.identity;

import java.util.Objects;

/**
 * Server-resolved owner and personal-space scope.
 *
 * <p>This value carries scope; it is not proof of authentication. Application
 * adapters must construct it only after resolving a trusted principal and
 * authorizing access to the selected personal space.</p>
 */
public record PersonalSpaceScope(OwnerId ownerId, PersonalSpaceId personalSpaceId) {
    public PersonalSpaceScope {
        Objects.requireNonNull(ownerId, "ownerId");
        Objects.requireNonNull(personalSpaceId, "personalSpaceId");
    }
}

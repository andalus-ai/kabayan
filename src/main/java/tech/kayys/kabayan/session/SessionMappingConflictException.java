package tech.kayys.kabayan.session;

/** Raised when a Kabayan session is already linked to a different Runtime session. */
public final class SessionMappingConflictException extends RuntimeException {
    public SessionMappingConflictException() {
        super("Kabayan session is already linked to a different Runtime session reference");
    }
}

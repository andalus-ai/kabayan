package tech.kayys.kabayan.session;

/**
 * Distinguishes external Runtime resource identifiers. These kinds are not
 * interchangeable, even when a provider represents them as strings.
 */
public enum RuntimeResourceKind {
    PROJECT_SESSION,
    OPERATOR_SESSION,
    RUN,
    EXECUTION,
    ATTEMPT,
    CHECKPOINT;

    public boolean isSession() {
        return this == PROJECT_SESSION || this == OPERATOR_SESSION;
    }
}

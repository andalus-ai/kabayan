package tech.kayys.kabayan.session;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import org.junit.jupiter.api.Test;
import tech.kayys.kabayan.identity.OwnerId;
import tech.kayys.kabayan.identity.PersonalSpaceId;
import tech.kayys.kabayan.identity.PersonalSpaceScope;

class RuntimeSessionMappingTest {
    @Test
    void rejectsRunOrExecutionReferenceAsSession() {
        var scope = new PersonalSpaceScope(new OwnerId("owner"), new PersonalSpaceId("space"));
        var run = new RuntimeResourceRef("andalus", RuntimeResourceKind.RUN, "run-1", "test");
        var now = Instant.parse("2026-11-28T00:00:00Z");

        assertThrows(IllegalArgumentException.class,
                () -> RuntimeSessionMapping.create(scope, new KabayanSessionId("session-1"), run, now));
    }

    @Test
    void rejectsNegativeRevision() {
        var scope = new PersonalSpaceScope(new OwnerId("owner"), new PersonalSpaceId("space"));
        var session = new RuntimeResourceRef("andalus", RuntimeResourceKind.OPERATOR_SESSION, "session-1", "test");
        var now = Instant.parse("2026-11-28T00:00:00Z");

        assertThrows(IllegalArgumentException.class,
                () -> new RuntimeSessionMapping(java.util.UUID.randomUUID(), scope,
                        new KabayanSessionId("session-1"), session, now, now, -1));
    }
}

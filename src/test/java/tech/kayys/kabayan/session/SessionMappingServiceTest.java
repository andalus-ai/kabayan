package tech.kayys.kabayan.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import tech.kayys.kabayan.identity.OwnerId;
import tech.kayys.kabayan.identity.PersonalSpaceId;
import tech.kayys.kabayan.identity.PersonalSpaceScope;

class SessionMappingServiceTest {
    private static final Instant NOW = Instant.parse("2026-11-28T00:00:00Z");
    private static final Clock CLOCK = Clock.fixed(NOW, ZoneOffset.UTC);

    @Test
    void sameLinkIsIdempotent() {
        var repository = new InMemoryRepository();
        var service = new SessionMappingService(repository, CLOCK);
        var scope = scope("owner-a", "space-a");
        var kabayanId = new KabayanSessionId("conversation-1");
        var runtimeRef = runtimeSession("project-session-1");

        var first = service.link(scope, kabayanId, runtimeRef);
        var second = service.link(scope, kabayanId, runtimeRef);

        assertEquals(first, second);
        assertEquals(1, repository.values.size());
    }

    @Test
    void differentRuntimeSessionCannotSilentlyRebindKabayanSession() {
        var service = new SessionMappingService(new InMemoryRepository(), CLOCK);
        var scope = scope("owner-a", "space-a");
        var kabayanId = new KabayanSessionId("conversation-1");

        service.link(scope, kabayanId, runtimeSession("project-session-1"));

        assertThrows(SessionMappingConflictException.class,
                () -> service.link(scope, kabayanId, runtimeSession("project-session-2")));
    }

    @Test
    void differentOwnersHaveIndependentMappings() {
        var repository = new InMemoryRepository();
        var service = new SessionMappingService(repository, CLOCK);
        var kabayanId = new KabayanSessionId("same-client-generated-id");

        var ownerA = service.link(scope("owner-a", "space-a"), kabayanId, runtimeSession("runtime-a"));
        var ownerB = service.link(scope("owner-b", "space-b"), kabayanId, runtimeSession("runtime-b"));

        assertNotEquals(ownerA.runtimeSession(), ownerB.runtimeSession());
        assertEquals(2, repository.values.size());
    }

    @Test
    void runReferenceCannotBeStoredAsSessionMapping() {
        var repository = new InMemoryRepository();
        var service = new SessionMappingService(repository, CLOCK);
        var run = new RuntimeResourceRef("andalus", RuntimeResourceKind.RUN, "run-1", "test");

        assertThrows(IllegalArgumentException.class,
                () -> service.link(scope("owner-a", "space-a"), new KabayanSessionId("s1"), run));
    }

    private static PersonalSpaceScope scope(String owner, String space) {
        return new PersonalSpaceScope(new OwnerId(owner), new PersonalSpaceId(space));
    }

    private static RuntimeResourceRef runtimeSession(String id) {
        return new RuntimeResourceRef("andalus", RuntimeResourceKind.PROJECT_SESSION, id, "test");
    }

    private static final class InMemoryRepository implements SessionMappingRepository {
        private final Map<Key, RuntimeSessionMapping> values = new HashMap<>();

        @Override
        public Optional<RuntimeSessionMapping> find(PersonalSpaceScope scope, KabayanSessionId sessionId) {
            return Optional.ofNullable(values.get(new Key(scope, sessionId)));
        }

        @Override
        public RuntimeSessionMapping createIfAbsent(RuntimeSessionMapping mapping) {
            var key = new Key(mapping.scope(), mapping.kabayanSessionId());
            var existing = values.putIfAbsent(key, mapping);
            if (existing == null) {
                return mapping;
            }
            if (!existing.runtimeSession().equals(mapping.runtimeSession())) {
                throw new SessionMappingConflictException();
            }
            return existing;
        }

        @Override
        public boolean delete(PersonalSpaceScope scope, KabayanSessionId sessionId) {
            return values.remove(new Key(scope, sessionId)) != null;
        }
    }

    private record Key(PersonalSpaceScope scope, KabayanSessionId sessionId) {
    }
}

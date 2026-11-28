# ADR 0001: Java/Maven core bootstrap

- Status: Accepted for the initial M1 foundation
- Date: 2026-11-28
- Decision owners: Kabayan maintainers

## Context

The Kabayan repository was documentation-only at the start of M1. There was no source tree, build descriptor, dependency policy or CI build. The surrounding Andalus ecosystem is Java-first and its current Framework and Runtime parent builds use Maven, Java release 25 and Quarkus 3.32.2.

Kabayan must remain a product layer and should not duplicate the neutral Runtime. M1 needs stable domain identity and mapping contracts before selecting an HTTP/authentication provider or persistence engine.

## Decision

1. Start with one small Maven artifact, `tech.kayys.kabayan:kabayan-core`, targeting Java 25.
2. Keep the first slice framework-neutral and dependency-light; use JUnit 5 for tests.
3. Model owner/personal-space scope and Kabayan session identity as distinct value types.
4. Model external Runtime references with explicit provider, resource kind, resource ID and environment.
5. Persist the mapping through a repository port whose operations always require owner and personal-space scope.
6. Keep HTTP framework, authentication provider, database, migrations, UI and runtime transport out of this bootstrap until their contracts and deployment requirements are verified.
7. Add CI verification on Java 25.

## Alternatives considered

- **Quarkus application immediately:** rejected for this slice because no identity provider, deployment topology, database or remote Runtime binding has been selected. Introducing the app now would create configuration and security assumptions before the boundaries are verified.
- **Multi-module Maven build:** rejected as premature; no stable module APIs exist yet.
- **Direct dependency on Andalus SDK:** rejected because the current SDK entry point inspected in the Runtime repository is local/embedded-oriented and does not establish a remote authenticated product-client contract.
- **MCP as internal transport:** rejected because it adds an unnecessary protocol hop and is not required for typed first-party integration.

## Consequences

- The core can be built and tested independently of Andalus deployment and transport.
- No production persistence implementation exists yet; the repository port defines the required owner-scoped behavior.
- No authentication endpoint exists yet. Application adapters must resolve trusted identity before constructing `PersonalSpaceScope`.
- The next M1 increment must choose persistence and authentication/deployment integration based on concrete requirements, then add a thin application layer around these contracts.

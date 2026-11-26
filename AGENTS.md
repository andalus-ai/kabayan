# Repository guidance for coding agents

## Mission and ownership

This repository owns the Kabayan personal-agent product. Keep all Kabayan-specific product behavior here, including personal identity/profile semantics, personal memory policy, proactive workflows, user experience, connector consent and approvals.

Andalus Framework and Andalus Runtime are neutral shared infrastructure. Do not add Kabayan-specific classes, packages, docs, configuration, product policy or naming to either project. Prefer consuming existing Andalus contracts and APIs. Propose upstream changes only when a concrete reusable infrastructure gap is demonstrated, and keep the upstream change product-neutral.

Other ecosystem boundaries:
- Aljabr supplies shared mathematical, statistical, ML and computational capabilities.
- Cifr and Farabi remain specialist products.
- Kabayan integrates with these products through declared capabilities and explicit policy, not by absorbing their product responsibilities.

## Architecture principles

1. **Reuse before invention.** Inspect existing code and contracts before adding abstractions, protocols, registries, persistence layers or transports.
2. **No duplicate runtime.** Do not recreate generic agent orchestration, capability/plugin lifecycle, run execution or transport infrastructure already owned by Andalus.
3. **No premature module proliferation.** The module names in `docs/architecture/KABAYAN_ARCHITECTURE.md` are proposed boundaries. Start with the smallest coherent build; extract a module only when it has a stable API, clear ownership and independent tests.
4. **Headless first.** Core domain and runtime behavior must not require the web/desktop/voice UI.
5. **Typed, versioned contracts.** Avoid untyped maps and implicit wire formats at module and integration boundaries. Preserve compatibility deliberately.
6. **Policy is not transport-dependent.** Local/in-process and remote calls must enforce equivalent identity, authorization, tenant scope, policy, approval and audit requirements.
7. **User control is foundational.** Personal data capture, retention, proactive use and external sharing must be explicit and controllable.

## Personal data and memory

Kabayan owns the personal meaning and policy of memory. Reuse suitable Andalus storage, retrieval and knowledge infrastructure without delegating Kabayan's product semantics to it.

Model memory using the logical classes defined in the architecture:
- Episodic: events and interactions.
- Semantic: retained facts, entities and relationships.
- Procedural: preferences and recurring workflows.
- Prospective: intentions, commitments and future follow-ups.

For retained memory, preserve source/provenance, confidence, timestamps, owner/scope, sensitivity, retention and correction/deletion lineage. Distinguish user-confirmed information from inference. Never silently promote a hypothesis into a confirmed fact.

Any implementation that stores or derives personal data must consider inspection, correction, suppression, export and deletion, including derived indexes and caches. Enforce owner/tenant isolation on every read, write and retrieval path.

## Security and external actions

- Apply least privilege to connectors and capability invocations.
- A connected account is not blanket consent.
- Authenticate remote callers and authorize every operation; client-supplied identity or tenant values are not proof of identity.
- Evaluate policy at execution time, not only during discovery.
- Require explicit approval for consequential external actions unless a narrowly scoped user-configured automation authorizes them.
- Make approvals scoped, expiring and single-purpose where supported.
- Audit access decisions, capability invocations, approvals, memory mutations and connector actions.
- Treat retrieved content, tool output, files, messages and client metadata as untrusted input.
- Never bypass controls for in-process calls or performance.

Do not log secrets, access tokens, raw sensitive content or unnecessary personal data. Document any unavoidable sensitive-data handling.

## Andalus integration / ARIP

Use `docs/architecture/ARIP_INTERACTION_PROFILE.md` as a proposal, not a frozen protocol. Before implementing client contracts:
1. Inspect current Framework communication contracts and Runtime REST/gRPC APIs.
2. Map each required operation to an existing endpoint or service.
3. Record actual gaps and compatibility implications.
4. Prefer existing typed APIs, streaming and local interfaces.
5. Add a new contract only after review demonstrates it is necessary.

MCP is for external tool interoperability, not a mandatory internal Kabayan-to-Andalus hop. Do not introduce a new ARIP transport, wire protocol, discovery mechanism or duplicate session/run/approval model without an accepted ADR and evidence.

## Implementation workflow

Before changing code:
1. Inspect the repository tree, build files, existing conventions and relevant tests.
2. Read the architecture document and applicable ADRs.
3. Identify the owner of each changed responsibility and the public compatibility surface.
4. Keep the change narrow; do not create speculative scaffolding or empty modules.

When implementing:
- Follow the language, formatting, dependency and test conventions already established in the repository. Do not assume a build system or framework before inspecting it.
- Keep domain logic independent from UI, transport and persistence details where practical.
- Prefer explicit types, immutable value objects where appropriate, clear error semantics and cancellation/deadline propagation.
- Validate input at trust boundaries.
- Avoid adding dependencies for functionality already available in the project or JDK.
- Add focused unit tests and integration/contract tests for changed boundaries.
- Update architecture docs and examples when behavior or contracts change.

Before completing a change:
- Run the narrowest relevant tests, then the full project verification available.
- Report exact commands and outcomes. If a command cannot run, state why; never imply it passed.
- Review the diff for accidental generated files, secrets, unrelated changes and broken links.
- Do not claim a feature, endpoint, benchmark or compatibility guarantee exists unless verified.

## Documentation and decisions

Keep documentation in sync with implementation. Use Mermaid for architecture/sequence diagrams when a diagram materially clarifies ownership or flow.

Add an ADR under `docs/architecture/decisions/` for decisions that materially affect:
- product/infrastructure ownership;
- personal memory semantics or lifecycle;
- authentication, authorization, consent or approval guarantees;
- public APIs, persistence schemas or external protocols;
- module boundaries or deployment topology.

Each ADR should include status, context, decision, alternatives, consequences and migration/compatibility notes. Do not create an ADR for routine implementation details.

## Pull requests

PRs should state:
- the user-visible or infrastructure outcome;
- ownership boundaries affected;
- security/privacy implications;
- tests and exact verification performed;
- documentation/ADR updates;
- known limitations and follow-up work.

Keep PRs reviewable and milestone-scoped. Do not merge or close a PR unless explicitly requested.

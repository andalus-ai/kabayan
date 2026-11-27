# Kabayan

Kabayan is a persistent personal AI agent that helps its owner understand, organize, plan and act across their digital life. It provides continuity, personal memory, proactive assistance and policy-controlled integrations.

This repository is the sole home for Kabayan product architecture and implementation. **Andalus Framework and Andalus Runtime remain neutral, reusable infrastructure.** Kabayan consumes their contracts and runtime services rather than moving product-specific behavior into those projects.

## Architecture and engineering docs

- [System architecture](docs/architecture/KABAYAN_ARCHITECTURE.md) — ownership, components, deployment, domain model and end-to-end flows.
- [Personal memory design](docs/architecture/PERSONAL_MEMORY.md) — memory classes, provenance, lifecycle, retrieval and user controls.
- [Security and privacy](docs/architecture/SECURITY_AND_PRIVACY.md) — trust boundaries, threat model, grants, approvals and data governance.
- [Implementation roadmap](docs/architecture/IMPLEMENTATION_ROADMAP.md) — milestone work packages, dependencies and acceptance criteria.
- [ARIP interaction profile](docs/architecture/ARIP_INTERACTION_PROFILE.md) — integration proposal; shared ARIP evolution is handled separately.
- [ARIP implementation mapping](docs/architecture/ARIP_IMPLEMENTATION_MAPPING.md) — source-audited Runtime mapping and known gaps.
- [Architecture documentation index](docs/architecture/README.md).

## Delivery direction

| Milestone | Focus |
|---|---|
| M0 | Architecture, ownership, security invariants and engineering conventions |
| M1 | Agent foundation, authenticated identity, sessions, typed Andalus client and run lifecycle |
| M2 | Personal memory, provenance, profile, retrieval and user controls |
| M3 | Proactive triggers, reminders, goals and durable background jobs |
| M4 | User-authorized connectors and integrations |
| M5 | Web/desktop/voice experience, activity timeline and transparency controls |
| M6 | Advanced multimodal and long-running workflows |

The roadmap is a sequence of validated outcomes, not a commitment to create every proposed module immediately.

## Project status

The repository is establishing its M0 architecture baseline. Proposed modules and conceptual operations are not implemented APIs. Implementation choices must be based on repository evidence, security review and compatibility with existing Andalus contracts.

## Contributing

Read [AGENTS.md](AGENTS.md) before making changes. Keep changes scoped, testable and consistent with ownership boundaries. Material changes to ownership, personal-data semantics, security guarantees, public APIs or deployment topology require an Architecture Decision Record (ADR).

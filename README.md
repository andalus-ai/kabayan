# Kabayan

Kabayan is a persistent personal AI agent that helps its owner understand, organize, plan and act across their digital life. It provides continuity across conversations and projects, personal memory, proactive assistance and policy-controlled integrations.

This repository is the sole home for Kabayan product architecture and implementation. **Andalus Framework and Andalus Runtime remain neutral, reusable infrastructure**; Kabayan consumes their contracts and runtime services rather than moving product-specific behavior into those projects.

## Architecture

- [Kabayan architecture and roadmap](docs/architecture/KABAYAN_ARCHITECTURE.md)
- [ARIP interaction profile](docs/architecture/ARIP_INTERACTION_PROFILE.md)
- [Architecture documentation index](docs/architecture/README.md)

ARIP is a proposed typed interaction profile over existing Andalus APIs, not a new transport or protocol stack. Its mapping must be validated against the current Runtime implementation before public contracts are frozen.

## Delivery direction

| Milestone | Focus |
|---|---|
| M0 | Architecture, ownership, security invariants and engineering conventions |
| M1 | Agent foundation, sessions, typed Andalus client and run lifecycle |
| M2 | Personal memory, provenance, profile and user controls |
| M3 | Proactive triggers, reminders and goals |
| M4 | User-authorized connectors and integrations |
| M5 | Web/desktop/voice experience and transparency controls |
| M6 | Advanced multimodal and long-running workflows |

See the architecture document for milestone dependencies and exit criteria. The roadmap is a sequence of validated outcomes, not a commitment to create every proposed module immediately.

## Project status

The repository is establishing its M0 baseline. Do not treat proposed module names or ARIP operations as implemented APIs. Implementation choices must be based on repository evidence and compatibility with the existing Andalus contracts.

## Contributing

Read [AGENTS.md](AGENTS.md) before making changes. Keep changes scoped, testable and consistent with the ownership boundaries. Architecture changes that affect project boundaries, personal-data semantics, security guarantees or external contracts require an Architecture Decision Record (ADR).

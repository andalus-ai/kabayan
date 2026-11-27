# Kabayan architecture documentation

This directory documents Kabayan product architecture and implementation constraints. Kabayan-specific product policy and personal-data semantics belong here; Andalus Framework and Runtime remain neutral infrastructure.

## Core design
- [System architecture](KABAYAN_ARCHITECTURE.md) — logical components, deployment, domain identity, conversation/run flows, proactive execution, connector flow, security and roadmap.
- [Personal memory design](PERSONAL_MEMORY.md) — memory classes, provenance, epistemic status, capture/retrieval, correction, retention, export and deletion.
- [Security and privacy](SECURITY_AND_PRIVACY.md) — threat model, trust boundaries, consent, approvals, connector grants, audit and incident controls.
- [Implementation roadmap](IMPLEMENTATION_ROADMAP.md) — milestone work packages, dependencies, definition of done and acceptance criteria.

## Andalus integration references
- [ARIP interaction profile](ARIP_INTERACTION_PROFILE.md) — proposed typed interaction semantics; shared ARIP design/evolution is handled separately.
- [ARIP implementation mapping](ARIP_IMPLEMENTATION_MAPPING.md) — source-level Runtime API mapping, evidence and unresolved gaps.

## Decision records
Material decisions belong under `decisions/` and should include status, context, decision, alternatives, consequences and migration notes. A proposal is not automatically an accepted implementation contract.

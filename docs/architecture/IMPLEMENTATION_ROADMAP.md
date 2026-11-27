# Implementation Roadmap

Status: M0 planning baseline  
Owner: Kabayan

This roadmap defines outcomes and evidence gates. Candidate module names do not require creating modules before implementation needs them.

## Milestones

| Milestone | Outcome | Depends on |
|---|---|---|
| M0 — Architecture | Product boundaries, domain model, security invariants and integration map | Repository baseline |
| M1 — Agent foundation | Authenticated conversation, session mapping, typed Runtime client and run lifecycle | Verified Runtime behavior |
| M2 — Personal memory | Owner-scoped memory, provenance and user controls | M1 owner identity; storage decision |
| M3 — Proactive intelligence | Durable triggers, reminders, goals and bounded jobs | M1 run lifecycle; memory policy; approvals |
| M4 — Integrations | Scoped connectors and external actions | Grant and secret model |
| M5 — Experience | Web/desktop/voice and transparency | Stable domain/API contracts |
| M6 — Advanced intelligence | Multimodal, cross-project and long-running workflows | Reliability/privacy evidence |

## M0 — Architecture baseline

Work: confirm ownership/non-goals; define identity domains; document memory lifecycle; define trust/approval invariants; audit Runtime APIs without redefining ARIP; establish guidance and docs index.

Exit: diagrams and docs reviewed, no duplicate runtime/transport, gaps explicit, M1/M2 criteria testable.

## M1 — Agent foundation

Work packages:
1. Inspect actual build system, Java/runtime baseline, dependencies and CI; establish smallest working service layout and health/readiness.
2. Resolve owner/personal-space scope at trusted edge; propagate typed context; reject caller identity spoofing.
3. Implement Kabayan conversation/session IDs and durable explicit mappings to external Runtime session refs.
4. Implement a narrow typed client over verified operations; keep REST/SSE and gRPC bindings behind facade where required; no speculative APIs or independent ARIP fork.
5. Preserve distinct run/execution/attempt IDs; define progress, terminal/error, timeout, cancellation and disconnect behavior.
6. Provide minimal headless conversation API and thin client; show activity, errors, approvals and cancellation state.

Acceptance:
- Authenticated owner can start/continue conversation.
- Session mapping survives restart and external ID kinds remain distinct.
- Request reaches a verified invocation surface.
- Progress/result/error/timeout semantics are typed and tested.
- Cancellation is observable and not falsely reported complete.
- Owner isolation, spoofing and unauthorized-resource tests pass.
- No new Runtime endpoint/protocol without separate evidence-backed decision.

## M2 — Personal memory

Work: owner-scoped typed model; provenance and epistemic status; capture/confirmation; scoped retrieval; consolidation/conflicts/freshness; inspect/correct/supersede/suppress/restore; deletion across derived stores; versioned export; profile separate from inference.

Acceptance: user can inspect source/status, correct/suppress immediately, delete with honest completion status and export a versioned owner-scoped archive. Isolation and derived-data deletion tests pass.

## M3 — Proactive intelligence

Work: goals/tasks/commitments; durable scheduler/queue; event normalization/deduplication; trigger evaluation and quiet hours; suggestion/reminder/action classification; approval-gated submission; cancellation/retry/dead-letter; activity timeline.

Acceptance: jobs survive restart and are effectively idempotent; revoked grants stop pending work; consequential actions require approval or narrowly configured automation; user can inspect/pause/cancel/delete jobs; retries are bounded.

## M4 — Integrations

Work: connector contract; secret vault and rotation; per-provider/account scopes and purpose; read-only ingestion first; signed/replay-protected webhooks; action previews and approval binding; revocation and dependent-job cleanup; provider contract tests.

Acceptance: grants independently revocable; tokens never appear in prompts/logs/memory; revocation blocks future work; external writes require approval or explicit narrow automation.

## M5 — Experience

Work: conversation/activity timeline; memory controls; approval center; connector permission center; goals/reminders workspace; accessible web/desktop; voice controls and recording/transcription disclosure; cross-device notifications.

Acceptance: owner can inspect why Kabayan remembers something and control it; pending actions and grants are visible; UI is a client of domain services, not owner of business rules.

## M6 — Advanced intelligence

Candidate work: multimodal context with provenance/consent; cross-project synthesis; long-running research/planning; local-first/hybrid execution; advanced consolidation and graph views. Start only after measurable quality, privacy, cost, resource and reliability targets exist.

## Parallelism and critical path

- M1 identity/session foundation and M2 memory schema design can proceed in parallel once owner identity invariants are agreed.
- M2 persistence depends on owner scope and reviewed storage choice.
- M3 scheduler groundwork can be prototyped during M2; production actions depend on durable jobs and approval.
- M4 read-only ingestion can proceed after grant/secret design; writes depend on approval.
- M5 UI can evolve alongside backend using versioned contracts and mocks; it cannot define domain semantics.
- M6 follows evidence from earlier milestones.

## Release discipline

Each PR identifies milestone/work package, includes tests/docs, states limitations and avoids claims about unimplemented contracts. A milestone is complete only when acceptance criteria pass in CI and security-negative tests exist.

# Personal Memory Design

Status: M0 product design; implementation and storage choices remain open  
Owner: Kabayan

## 1. Purpose and ownership

Personal memory provides continuity across conversations and tasks. Kabayan owns memory semantics, user controls and personal-data lifecycle. It may reuse Andalus storage, retrieval, vector, graph or knowledge infrastructure, but those systems do not thereby own Kabayan's personal-data policy.

Memory is not a transcript dump and model-generated content is not automatically true.

## 2. Memory classes

| Class | Meaning | Examples |
|---|---|---|
| Episodic | Sourced event or interaction | Meeting occurred; decision made in a conversation |
| Semantic | Retained fact or relationship | Project uses a language; person has a stated role |
| Procedural | Preference or recurring method | Preferred report format; recurring workflow |
| Prospective | Future intention or commitment | Follow up next week; prepare proposal by a date |

These are logical classes and may share one storage model.

## 3. Conceptual memory record

A record needs typed equivalents of:
- stable `memoryId`, `ownerId`, `personalSpaceId`;
- `memoryType` and typed content;
- `epistemicStatus`: user-confirmed, source-observed, inferred or proposed;
- confidence and sensitivity;
- provenance references, source type and source timestamp;
- created/observed/updated times and optional validity interval;
- retention policy and retrieval eligibility;
- state: proposed, active, suppressed, superseded, expired or deletion-pending;
- `supersedes[]` and `derivedFrom[]` lineage.

This is a conceptual schema, not a finalized database/API. Minimize raw content and never store secrets.

## 4. Capture lifecycle

```mermaid
stateDiagram-v2
  [*] --> Candidate
  Candidate --> ProvenanceChecked
  ProvenanceChecked --> PendingConfirmation: confirmation needed
  ProvenanceChecked --> Active: safe auto-retention policy
  ProvenanceChecked --> Rejected: irrelevant / sensitive / weak source
  PendingConfirmation --> Active: confirmed
  PendingConfirmation --> Rejected: rejected / expired
  Active --> Suppressed: user suppresses
  Active --> Superseded: corrected or replaced
  Active --> Expired: retention expires
  Suppressed --> Active: user restores
  Active --> DeletionPending: deletion requested
  Suppressed --> DeletionPending: deletion requested
  Superseded --> DeletionPending: deletion requested
  Expired --> DeletionPending: deletion requested
  DeletionPending --> Deleted: primary and derived data removed
  Rejected --> [*]
  Deleted --> [*]
```

```mermaid
sequenceDiagram
  autonumber
  participant Source as Conversation / connector
  participant Capture as Capture policy
  participant Extract as Extractor
  participant Prov as Provenance resolver
  participant Store as Memory store
  actor Owner
  Source->>Capture: Content + authenticated owner scope
  Capture->>Capture: Check purpose, sensitivity and retention
  alt Capture disallowed
    Capture-->>Source: Do not retain
  else Capture allowed
    Capture->>Extract: Extract typed candidates
    Extract->>Prov: Attach source and timestamps
    Prov-->>Extract: Provenance and epistemic status
    Extract-->>Capture: Candidate memory
    opt Confirmation required
      Capture->>Owner: Confirm / edit / reject
      Owner-->>Capture: Decision
    end
    Capture->>Store: Persist active or pending record
    Store-->>Capture: Memory reference
  end
```

Rules:
- Discussion of a sensitive topic is not consent to retain it.
- Uncertain/consequential facts remain hypotheses or require confirmation.
- Preserve source time separately from ingestion time.
- Deduplicate without erasing distinct events or conflicting evidence.
- Conflicting facts are versioned or clarified, never silently overwritten.

## 5. Retrieval lifecycle

```mermaid
flowchart TD
  Q[User request / task] --> AUTH[Authenticate owner and personal space]
  AUTH --> SCOPE[Apply purpose and sensitivity policy]
  SCOPE --> CAND[Retrieve owner-scoped candidates]
  CAND --> FILTER[Filter state, freshness and retention]
  FILTER --> RANK[Rank relevance, confidence and recency]
  RANK --> PROV[Attach provenance and uncertainty]
  PROV --> CTX[Build bounded task context]
  CTX --> MODEL[Agent reasoning / response]
  MODEL --> ANSWER[Answer with uncertainty]
  MODEL --> NEW[Optional memory candidates]
```

Apply owner scope before search, not only after global retrieval. Filter suppression, expiry, sensitivity and purpose before context assembly. Return provenance and epistemic status. Retrieval is read-only; writes follow a separate lifecycle.

## 6. Correction, suppression and deletion

```mermaid
sequenceDiagram
  autonumber
  actor Owner
  participant API as Memory API
  participant Auth as Owner-scope policy
  participant Store as Primary store
  participant Derived as Indexes / embeddings / caches
  participant Jobs as Queued jobs / summaries
  participant Audit as Audit ledger
  Owner->>API: Correct / suppress / delete
  API->>Auth: Verify owner and operation scope
  Auth-->>API: Authorized
  API->>Store: Versioned correction or tombstone
  Store-->>API: Mutation reference
  API->>Derived: Invalidate or rebuild derived data
  API->>Jobs: Cancel or re-evaluate dependent work
  Derived-->>API: Completion status
  Jobs-->>API: Completion status
  API->>Audit: Record action and evidence
  API-->>Owner: Result and outstanding exceptions
```

- Correction creates an auditable revision.
- Suppression prevents future retrieval/proactive use.
- Deletion covers primary records, embeddings, indexes, graph edges, summaries, caches, exports under Kabayan control and queued jobs.
- If a provider cannot confirm deletion, report it; never claim full deletion.
- Restores must reapply deletion tombstones before data becomes queryable.
- Source revocation triggers review of memories derived solely from that source.

## 7. Export and retention

Export includes active/user-visible memory, profile/preferences, provenance/status, user-authored plans and reminders, connector metadata/grants (never secrets), schema version and timestamp. It is authenticated, owner-scoped, encrypted and completeness-reported. No arbitrary filesystem paths. Partial provider/store failures must be disclosed.

Classify sensitive data, minimize content, support configurable retention, separate memory retention from transcript retention, and do not use personal memory for model training without separate explicit revocable consent.

## 8. M2 work packages and acceptance

1. Owner-scoped typed model and repository contract.
2. Provenance, epistemic status, confidence and sensitivity.
3. Capture policy and confirmation workflow.
4. Scoped retrieval and filtering.
5. Consolidation, deduplication, conflicts and freshness.
6. Inspect, correct, supersede, suppress and restore.
7. Deletion across primary and derived stores.
8. Versioned export with completeness report.
9. Separate explicit profile from inferred memory.

Acceptance: user can inspect source/status, correct and suppress immediately, delete with honest completion status, and export a versioned owner-scoped archive. Isolation and derived-data deletion are tested.

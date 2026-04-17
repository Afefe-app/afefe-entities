# Environments, public surface, compliance, and clients

*Last aligned with product/engineering input for security audits.*

## Deployment status and target clouds

| Environment | Status | Hosting notes |
|-------------|--------|----------------|
| **Current (dev / staging)** | Active | **Contabo VPS** runs backend workloads and related infrastructure used for integration and pre-production testing. |
| **Production** | **Not live yet** | **Google Cloud Platform (GCP)** is the **likely** target for production. Architecture, networking, identity integration (e.g. Workload Identity), and data residency should be designed against GCP well before go-live. |

Auditors should treat **staging on Contabo** and **future production on GCP** as **separate trust zones** with distinct network diagrams, IAM models, and evidence packs.

## DNS, TLS, and edge

| Component | Role |
|-----------|------|
| **Cloudflare** | **Domain / DNS management** (and typically proxying, TLS, and security features depending on configuration). |
| **Public site / API entry** | **`https://afefe.com`** is the **canonical public URL** for the product as of documentation time. Confirm whether API traffic uses the same hostname, subdomains (e.g. `api.afefe.com`), or separate infrastructure. |

Reviewers should obtain: Cloudflare SSL mode, WAF rules, rate limiting, bot management, and whether origin (Contabo now, GCP later) is reachable only from Cloudflare IPs.

## Audit scope — all services in scope

The following are **in scope** for security assessments (configuration, code, deployment, and data flows), including when deployed only in non-production today:

- **afefe-entities** (shared library — identity primitives, security, domain, protos)
- **afefe-config**, **afefe-discovery**, **afefe-gateway**
- **afefe-iam**, **afefe-learner**, **afefe-instructor**, **afefe-notification**, **afefe-payment**
- **afefe-frontend**
- **afefe-waitlist**, **afefe-superadmin**, **afefe-profile**
- **CBI** and any other application folders in the workspace that ship to the same ecosystem

Third-party SaaS (Stripe, Cloudinary, GitHub, etc.) are **in scope** for integration review (webhooks, keys, data shared); their internal controls are covered by their own attestations.

## Client types

| Client | How it typically interacts |
|--------|----------------------------|
| **Web** | Browser → **afefe-frontend** (and/or gateway) — HTTP, gRPC-web / Connect-style calls as implemented. |
| **Mobile** | Native or hybrid apps → same backend APIs (HTTP/gRPC as exposed) — treat token storage, certificate pinning policy, and deep links as review items. |
| **Inter-service** | Microservices call each other (HTTP, gRPC, messaging) within the platform — mTLS, network policies, and service identity should be explicit in production (especially on GCP). |

## Compliance — intended coverage

The organization intends to satisfy **applicable** requirements across jurisdictions and industries as the product and customer base grow. Audits should map controls to the frameworks that matter for your markets; common mappings for a global EdTech + payments stack include:

| Area | Typical frameworks / standards |
|------|--------------------------------|
| **Privacy (EU/UK)** | GDPR, UK GDPR — lawful basis, DPIA, DSRs, subprocessors, cross-border transfers, SCCs. |
| **Privacy (US)** | CCPA/CPRA (and sector/state laws as applicable). |
| **Privacy (Nigeria / Africa)** | NDPR and local sector rules where users or entities are established. |
| **Payments** | PCI DSS — scope reduction via Stripe; webhook and checkout integration still in scope for SAQ or full assessment as applicable. |
| **Security assurance** | SOC 2 (Trust Services Criteria), ISO/IEC 27001 — often customer-driven for B2B. |
| **App security** | OWASP ASVS / Top 10, secure SDLC. |
| **Operational resilience** | NIST CSF (or regional equivalents), incident response and breach notification timelines tied to GDPR/NDPR/CCPA. |

**Operational note:** “All compliance targets” in practice means **prioritize by market launch order** and maintain a **control matrix** (control ID → owner → evidence location). This document does not replace legal counsel or a formal compliance program.

## Identity and JWT — implementation location

**JWT issuance, validation semantics, channel authentication, and gRPC security interceptors** are implemented in **`afefe-entities`** (see `core.security` and related packages) and consumed by **afefe-iam** and other services. IAM-specific HTTP flows live in **afefe-iam**; auditors should trace end-to-end from client through gateway to IAM and downstream gRPC calls.

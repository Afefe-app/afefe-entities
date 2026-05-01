# Ecosystem overview

## Purpose

Afefe is a **multi-service learning/education platform**. Backend services are primarily **Java 21 / Spring Boot 3.x**, with a **shared domain and gRPC layer** delivered by the `afefe-entities` module (JPA entities, Protocol Buffers, security primitives consumed by other services).

## Repository layout (workspace)

The parent workspace under `Afefe/` contains multiple deployable applications, including but not limited to:

| Area | Typical role |
|------|----------------|
| **afefe-entities** | Shared library: domain models, gRPC/proto contracts, cross-cutting security (JWT, channel auth, gRPC interceptors). |
| **afefe-config** | Spring Cloud Config Server — centralizes environment-specific YAML (`staging`, `production`, etc.). |
| **afefe-discovery** | Eureka service registry. |
| **afefe-gateway** | Spring Cloud Gateway — HTTP entry, routes to backend REST services. |
| **afefe-iam** | Identity / authentication-related HTTP + gRPC APIs. |
| **afefe-learner**, **afefe-instructor**, **afefe-notification**, **afefe-payment** | Domain microservices (HTTP + gRPC per service). |
| **afefe-frontend** | Next.js / TypeScript client (gRPC-web / Connect-style transport to backends). |
| **afefe-waitlist**, **afefe-superadmin**, **afefe-profile**, **CBI** | Additional applications — **in scope** for security audits (see [10-environments-compliance-clients.md](./10-environments-compliance-clients.md)). |

Not every folder may be built from a single Maven reactor; treat each service as an independently deployable unit unless your build docs state otherwise.

## Identity and security implementation

**JWT, channel-based client authentication, and gRPC security** are implemented in **`afefe-entities`** (`core.security`, JWT helpers, interceptors). **afefe-iam** exposes identity-related HTTP/gRPC APIs and uses those primitives; other services consume the same library. See [05-authn-authz.md](./05-authn-authz.md).

## Clients

**Web**, **mobile**, and **inter-service** callers are all first-class; reviewers should cover each path (tokens on device, service-to-service trust, and gateway exposure). Details: [10-environments-compliance-clients.md](./10-environments-compliance-clients.md).

## Staging / development infrastructure

- **Contabo VPS** hosts current **development and staging** workloads.
- **Cloudflare** manages the **domain** (DNS and, depending on setup, proxying/WAF in front of origins).
- **Public URL:** **`https://afefe.com`** — confirm API vs marketing split (subdomains, paths).
- **Production** is **not live**; **GCP** is the **likely** production cloud — plan controls (IAM, VPC, secrets, logging) accordingly.

Auditors should obtain: network diagrams, public vs private interfaces, firewall rules, SSH access model, Cloudflare ↔ origin trust, and backup posture from the operations team.

## CI/CD

- **GitHub Actions** implements a **lightweight pipeline**: typically Maven build, Docker build, and **remote deployment over SSH** to the target host. Details: [07-infrastructure-cicd.md](./07-infrastructure-cicd.md).

## Compliance

Broad intended coverage (privacy, payments integration, security assurance programs) is summarized in [10-environments-compliance-clients.md](./10-environments-compliance-clients.md).

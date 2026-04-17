# Afefe — Security audit documentation

This folder is the **entry point for cybersecurity reviewers** auditing the Afefe application ecosystem. It describes architecture, trust boundaries, authentication, data handling, infrastructure, and CI/CD at a level suitable for threat modeling and control testing.

**Operational context (provided by product/engineering):**

- **Public URL:** `https://afefe.com` (canonical as of documentation time).
- **Edge / DNS:** **Cloudflare** for domain management (and related edge controls as configured).
- **Current hosting:** **Contabo VPS** for development and staging workloads.
- **Production:** Not live yet; **GCP** is the likely production cloud — see [10-environments-compliance-clients.md](./10-environments-compliance-clients.md).
- **Clients in scope:** **Web**, **mobile**, and **inter-service** traffic all use the ecosystem.
- **Identity / JWT:** Implemented in **`afefe-entities`** (consumed by IAM and other services) — see [05-authn-authz.md](./05-authn-authz.md) and section 10.
- **CI/CD:** GitHub Actions (“mini CI/CD”) — build, Docker image, SSH deploy to configured servers.
- **Compliance:** Organization targets **broad regulatory and assurance coverage** (privacy, PCI-related integration, SOC 2 / ISO-style security programs as applicable) — see [10-environments-compliance-clients.md](./10-environments-compliance-clients.md).

**How to use these docs**

1. Read [10-environments-compliance-clients.md](./10-environments-compliance-clients.md) for scope, URL, Cloudflare/Contabo/GCP, compliance intent, and client types.
2. Continue with [01-ecosystem-overview.md](./01-ecosystem-overview.md) and [02-system-architecture.md](./02-system-architecture.md).
3. Use [03-services-inventory.md](./03-services-inventory.md) for ports and component roles.
4. Deep dives: [05-authn-authz.md](./05-authn-authz.md), [06-external-integrations.md](./06-external-integrations.md), [08-secrets-and-config.md](./08-secrets-and-config.md).
5. [09-security-audit-focus-areas.md](./09-security-audit-focus-areas.md) lists high-value review targets derived from the codebase patterns.

### Document index

| Document | Contents |
|----------|----------|
| [01-ecosystem-overview.md](./01-ecosystem-overview.md) | Repos, Contabo staging, CI/CD summary |
| [02-system-architecture.md](./02-system-architecture.md) | Config server, Eureka, gateway, data flow |
| [03-services-inventory.md](./03-services-inventory.md) | Ports and roles |
| [04-data-classification.md](./04-data-classification.md) | Data stores, domains, PII themes |
| [05-authn-authz.md](./05-authn-authz.md) | JWT, channel auth, gRPC security, CORS, Actuator |
| [06-external-integrations.md](./06-external-integrations.md) | Stripe, Cloudinary, Kafka, etc. |
| [07-infrastructure-cicd.md](./07-infrastructure-cicd.md) | GitHub Actions, deploy pattern, Contabo |
| [08-secrets-and-config.md](./08-secrets-and-config.md) | Where secrets live; policy notes |
| [09-security-audit-focus-areas.md](./09-security-audit-focus-areas.md) | Audit checklist |
| [10-environments-compliance-clients.md](./10-environments-compliance-clients.md) | Public URL, Cloudflare, Contabo, GCP, full scope, compliance, clients |

**Important:** This documentation intentionally does **not** reproduce passwords, API keys, or connection strings. Obtain current secret values and inventories from secure credential stores and from repository/configuration reviews under organizational policy.

**Related generated API surface:** Protocol Buffer definitions live in this repository under `src/main/proto/`; generated HTML docs may be published via GitHub Pages (see `.github/workflows/serve-documentation.yml` in `afefe-entities`).

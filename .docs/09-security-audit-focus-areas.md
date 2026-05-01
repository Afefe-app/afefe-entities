# Security audit focus areas

Use this as a **structured checklist** for penetration tests, configuration reviews, and architecture risk assessment.

## Identity and access

- [ ] Review **`afefe-entities`** (`core.security`, JWT properties) as the **canonical implementation** of token and channel-auth behavior; then validate **afefe-iam** and downstream services.
- [ ] Validate JWT lifecycle: issuance, refresh (if any), revocation, clock skew, key rotation.
- [ ] Test **Channel-ID / Channel-Secret** flows for brute force, enumeration, and rate limiting.
- [ ] Review **gRPC method-level** security metadata: ensure sensitive RPCs require both channel auth and tenant JWT where intended.
- [ ] Confirm **super-admin** and **CBI** surfaces are network-restricted and strongly authenticated.

## Network and transport

- [ ] **Cloudflare** (for `afefe.com`): WAF, SSL mode, DNS security, rate limits, origin protection; confirm no direct origin bypass.
- [ ] **Gateway** is the intended public HTTP entry — confirm backend HTTP ports are not exposed on the public internet.
- [ ] **gRPC ports (2003–2007)** — confirm binding and firewall rules; gRPC reflection enabled on several services increases discovery value for attackers if reachable.
- [ ] **Inter-service** calls: no implicit trust from “private network” alone — design for **GCP** (VPC SC, private GKE, or equivalent) with explicit service identity.
- [ ] **TLS:** Validate HTTPS for **`https://afefe.com`** and subdomains; TLS for DB/Kafka where supported.
- [ ] **CORS** policy vs actual web and mobile origins — wildcard (`*`) may be acceptable only when credentials are not used; verify.

## Clients (web, mobile)

- [ ] **Mobile:** secure token storage (Keychain/Keystore), deep link handling, SSL pinning policy if used.
- [ ] **Web:** cookie flags, XSS/CSRF posture for any session or token-in-cookie flows.

## Configuration and secrets

- [ ] **Spring Cloud Config** — access control, encryption at rest, audit of who can read production config.
- [ ] **Actuator** — restrict endpoints; avoid exposing env, heap dumps, or shutdown on untrusted networks.
- [ ] Search codebase for **hardcoded** webhook signing placeholders, API keys, or default passwords (payment and auth modules are priority).
- [ ] **Git history** — secret scanning on all repos in the ecosystem.

## Data protection

- [ ] PostgreSQL: encryption at rest (disk), TLS in transit, role separation (app vs migration vs read-only).
- [ ] Kafka: retention, encryption, consumer authorization, PII in events.
- [ ] Logs: redaction of tokens, passwords, and health payloads.

## CI/CD and hosting

- [ ] **GitHub Actions:** scope of `SSH_PRIVATE_KEY`, host hardening, whether deploy uses `root`.
- [ ] **Contabo / VPS (current staging):** OS patching, SSH key-only auth, fail2ban, minimal open ports.
- [ ] **GCP (planned production):** IAM, VPC boundaries, Secret Manager, Cloud Logging/Monitoring, least-privilege service accounts — align before go-live.
- [ ] **Docker:** `--network host` usage in some production workflows — understand implications for isolation.

## Compliance and privacy (cross-framework)

Map controls to the frameworks in [10-environments-compliance-clients.md](./10-environments-compliance-clients.md). Typical evidence areas:

- [ ] **Records of processing / RoPA**, lawful basis, and subprocessors (including Stripe, Cloudflare, GCP when live).
- [ ] **Data subject rights** process (access, erasure, portability) across PostgreSQL, Kafka, logs, backups.
- [ ] **Cross-border transfers** (EU ↔ US ↔ Africa) — SCCs and transfer impact assessments as needed.
- [ ] **PCI:** scope document for Stripe integration; webhook and redirect security.
- [ ] **Breach / incident** notification playbooks aligned to GDPR (72h where applicable), NDPR, CCPA, and other applicable timelines.

## Application logic

- [ ] **Stripe webhooks:** signature verification only with current secrets; idempotency of payment processing.
- [ ] **File uploads:** size limits (multipart max in config), content-type validation, virus scanning if required.
- [ ] **Authorization:** horizontal privilege (org A vs org B) on learner/instructor APIs.

## Dependency supply chain

- [ ] Maven dependencies and **Tensorpoint** internal artifacts (`com.tensorpoint.toolkit:*`) — versioning, provenance, CVE monitoring.
- [ ] npm/frontend dependencies — audit for known vulnerabilities.

## Incident readiness

- [ ] Contact points for rotating Stripe, DB, JWT, and Cloudinary credentials.
- [ ] Logging and alerting to SIEM for auth failures and payment anomalies.

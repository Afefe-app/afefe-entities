# Secrets and configuration management

## Where configuration lives

1. **Spring Cloud Config Server** (`afefe-config`) — YAML per profile under `src/main/resources/{profile}/` (e.g. `staging/common.yml`, `staging/learner.yml`, `production/common.yml`).
2. **Per-service `application.yml`** — bootstrap and Eureka/Config Server connection.
3. **GitHub Actions secrets** — deployment SSH keys, server IP, ports, Kafka bootstrap servers.
4. **Client-side** — Next.js `NEXT_PUBLIC_*` variables (browser-visible).

## Policy expectations for auditors

- **No secrets in git:** If databases, API keys, or signing secrets appear in committed YAML, treat as **incident** (rotate credentials, remove from history per org process).
- **Separation:** Production secrets should be injected via vault, sealed secrets, or CI/CD secrets — not copied into developer laptops. For **GCP** production, plan **Secret Manager** (or equivalent) and avoid long-lived keys in VM metadata.
- **Cloudflare:** API tokens / account access for DNS and security settings are high-value — protect like production credentials.
- **JWT keys:** Must be environment-specific and rotated on compromise; verify `jwt.security.properties` overrides in config for each environment.

## Configuration items to inventory (categories only)

- JDBC URLs and DB credentials  
- Kafka bootstrap servers and auth  
- Stripe secret key and webhook signing secret  
- Cloudinary (or media) API keys  
- Internal channel / Feign shared secrets  
- Any OAuth or bank API tokens  
- SSH deploy keys (GitHub + servers)

## Hibernate DDL

- `ddl-auto: update` in shared YAML — confirm production uses controlled migrations (Flyway/Liquibase) if required by policy.

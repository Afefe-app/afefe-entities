# External integrations

This section lists **third-party and internal integration points** commonly configured in Afefe. Exact endpoints and keys must be taken from secure configuration — not from this document.

## Payments — Stripe

- **afefe-payment** configures a Stripe Java client using `payments.gateway.stripe.secret-key` (from Spring Cloud Config).
- **HTTP webhook:** `POST` under `/stripe/webhook` — typically verifies **Stripe-Signature** using a webhook signing secret. Auditors should confirm secrets are loaded from secure config/secret store, **not** hardcoded fallbacks, and that webhook URLs are HTTPS in production.
- **PCI scope:** Card data handling is usually delegated to Stripe; still review redirect/checkout flows and what data is logged.

## Media — Cloudinary (or equivalent)

- Used for uploads and media URLs. Review signed upload policies, folder isolation, and whether transformations expose private content.

## Banking / other APIs

- Other repositories (e.g. **CBI**) may reference **banking APIs** or middleware — treat as **high criticality**: mTLS, IP allowlists, token rotation, audit logging.

## Email / notifications

- **afefe-notification** — confirm providers (SMTP, SendGrid, etc.) from config and how unsubscribe/preferences are enforced (`NotificationPreference` entities exist in `afefe-entities`).

## Infrastructure services

| Integration | Security considerations |
|-------------|-------------------------|
| **PostgreSQL** | Network isolation, least-privilege DB users, TLS, rotation |
| **Kafka** | Authentication (SASL), TLS, topic ACLs, no sensitive payloads in clear logs |
| **NATS** (if enabled) | Auth, TLS, exposure |
| **Eureka** | Should not be internet-facing; authentication if supported |
| **Spring Cloud Config** | Protect config server; contains secrets in many deployments |

## feign.internal / inter-service headers

- Shared config may define **internal** HTTP client identifiers (`feign.internal.app-user` with channel id/secret). Verify these are not guessable defaults in production and are rotated if leaked.

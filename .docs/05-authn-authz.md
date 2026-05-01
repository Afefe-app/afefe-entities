# Authentication and authorization

## Where identity logic lives

**JWT handling, channel-based application authentication, tenant resolution, and gRPC security interceptors** are implemented in the **`afefe-entities`** module (e.g. `com.ocean.afefe.entities.core.security`). **afefe-iam** and other services depend on this library; end-to-end reviews should start from `afefe-entities` for crypto and token semantics, then validate each service’s use of `SecurityModule` and method-level metadata.

## HTTP layer

- **CORS:** Several services and the gateway configure **permissive CORS** (e.g. `Access-Control-Allow-Origin: *`, broad headers). Auditors should confirm whether this matches production threat model (credentials, cookies, custom headers).
- **Security path exclusions:** Shared config lists paths such as Swagger UI, OpenAPI docs, Stripe webhook, and result pages as excluded from certain security filters — verify each exclusion is still required and cannot be abused.

## gRPC layer (afefe-entities)

The `SecurityModule` wires:

- **Channel authorization (HTTP + gRPC):** Validates **Channel-ID** and **Channel-Secret** metadata against stored app user records (BCrypt or similar for channel secret). Methods can be marked “no auth” via **Tensorpoint** `GrpcSecurityMetadataRegistry` / `MethodSecurityMetadata`.
- **Tenant / user context (gRPC):** After channel auth, **Bearer** token in metadata may be required for tenant-scoped operations; JWT is used to resolve organization and membership.
- **Order of interceptors:** Implemented as global gRPC server interceptors (`@GrpcGlobalServerInterceptor`).

## JWT configuration

- JWT-related settings are bound via `@ConfigurationProperties("jwt.security.properties")` (`JwtConfigurationProperties`). **Auditors must verify** whether signing keys and expiry values are overridden per environment and **not** left at library defaults.

## Frontend

- The web client sends **Authorization: Bearer** and may send **Channel-ID / Channel-Secret** (including from `NEXT_PUBLIC_*` env vars where used). **Public env vars** are visible in the browser — never place long-lived secrets there; treat channel secrets as compromised if exposed client-side.

## IAM gRPC vs HTTP

- `afefe-iam` sets `grpc.server.security.enabled: false` in YAML — meaning **transport-level TLS for gRPC is not enabled by that flag**; encryption must come from the network layer (e.g. reverse proxy, mesh, or host isolation). Confirm production architecture.

## Management endpoints

- Spring Boot Actuator configuration in shared YAML may expose **broad endpoint exposure** (`management.endpoints.web.exposure.include: '*'`). Treat as **high priority** for network restriction and authentication in production.

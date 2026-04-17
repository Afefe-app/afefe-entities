# Services inventory (default ports)

**Audit scope:** All services listed below — plus **afefe-waitlist**, **afefe-superadmin**, **afefe-profile**, and **CBI** — are **in scope** unless formally excluded by the organization. See [10-environments-compliance-clients.md](./10-environments-compliance-clients.md).

Ports below come from each service’s `src/main/resources/application.yml` in the workspace. **Production may remap ports** via Docker or host networking; confirm on the target environment (future **GCP** deployment may use different exposure patterns than **Contabo** staging).

| Service | HTTP port | gRPC port | Notes |
|---------|-----------|------------|--------|
| afefe-discovery (Eureka) | 1000 | — | Registry |
| afefe-config | 1001 | — | Spring Cloud Config Server |
| afefe-gateway | 1002 | — | Reactive gateway; routes to backends |
| afefe-iam | 1003 | 2003 | gRPC reflection enabled in YAML |
| afefe-notification | 1004 | 2004 | |
| afefe-instructor | 1005 | 2005 | gRPC reflection enabled in YAML |
| afefe-learner | 1006 | 2006 | gRPC reflection enabled in YAML |
| afefe-payment | 1007 | 2007 | gRPC reflection enabled in YAML |
| CBI (separate app) | 1008 | — | Distinct product area; own profiles |
| afefe-superadmin | 1010 | — | Administrative UI/API (verify scope) |

## afefe-entities (library, not a standalone port)

- **Artifact:** Maven module `com.ocean.afefe:entities` — consumed by microservices.
- **Contains:** JPA entities/repositories, `.proto` files under `src/main/proto/`, security module (JWT, channel auth, gRPC interceptors).

## Protocol buffer contracts

Proto files (indicative API domains): `Auth`, `Course`, `LearnerCourse`, `LearnerProfile`, `InstructorProfile`, `InstructorFinance`, `Payment`, `Wallet`, `Calendar`, `Onboarding`, `Settings`, `Hr`, `WaitList`, `Generic`, `Common`. These define the **gRPC service surface** auditors should align with generated server stubs in each service.

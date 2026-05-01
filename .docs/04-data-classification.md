# Data classification and stores

## Primary relational database

- **PostgreSQL** is used in staging configuration (`afefe-config` profile `staging`) with JDBC URLs under `spring.datasource.*`.
- **Hibernate `ddl-auto: update`** appears in shared config for some profiles — auditors should confirm whether this is acceptable for production (schema drift risk vs controlled migrations).

## Data domains (from `afefe-entities` modules)

Reviewers should map entities to **PII / sensitive** categories for their jurisdiction:

| Module / area | Examples of sensitive data themes |
|---------------|-----------------------------------|
| **auth** | Users, org membership, super-admin records, notification preferences, channel credentials (hashed secrets for API clients). |
| **contents** | Courses, lessons, assets, ratings, learning paths, content notes. |
| **enrollments** | Enrollment state, lesson progress. |
| **assessment** | Quizzes, attempts. |
| **analytics** | User activity / action types. |
| **payment** | Payment transactions, enrollment payment links (financial). |
| **helpcenter** | Support tickets, messages, contact inquiries, help articles. |

## MongoDB

- `afefe-entities` declares **spring-boot-starter-data-mongodb**; confirm whether MongoDB is used in runtime for any service and which collections hold PII.

## Object storage / media

- **Cloudinary** (or similar) appears in shared configuration for media — credentials are **high sensitivity**; confirm signed URLs, access control, and data residency.

## Messaging

- **Apache Kafka** is configured for event/outbox style integration (`tpoint` / broker configuration in shared YAML). Topics may carry PII — review serialization, retention, and access control.

## Optional messaging

- **NATS** may appear in configuration with an `enable` flag — verify if used in the audited environment.

## Logging

- Verbose Kafka logging flags may appear in config — assess whether logs could leak message payloads or PII.

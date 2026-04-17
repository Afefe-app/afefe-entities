# Infrastructure and CI/CD

## Edge and DNS

- **Cloudflare** is used for **domain management** for **`afefe.com`**. Collect: DNS records, SSL/TLS mode, WAF rules, rate limits, and whether the origin (Contabo today) is restricted to Cloudflare IPs.

## Hosting (current: staging / dev)

- **Contabo VPS** runs **development and staging** environments today. For audits, collect:
  - Server inventory (OS, patch level)
  - Network exposure (public IPs, open ports, SSH, Docker API)
  - Whether services bind `0.0.0.0` vs localhost
  - Firewall (ufw/iptables/cloud firewall) and fail2ban or equivalent
  - Backup and disaster recovery for databases and volumes

## Production (planned)

- **Production is not live yet.** **Google Cloud Platform (GCP)** is the **likely** target. CI/CD, secrets (Secret Manager), networking (VPC, Private Service Connect), and IAM should be designed before launch; evidence for audits will differ from Contabo staging.

## GitHub Actions (mini CI/CD)

Workflows live in each service repository under `.github/workflows/`. Common patterns observed:

### Staging deploy (`deploy-staging.yml`)

- **Triggers:** Push or PR to `staging` branch (exact triggers vary per repo — confirm in each file).
- **Runner:** `ubuntu-latest`.
- **Build:** JDK 21 (Temurin), `mvn clean package -DskipTests`.
- **Deploy:** SSH to remote host using **`SSH_PRIVATE_KEY`** secret; remote steps typically:
  - `cd /root/backends/${FOLDER_NAME_IN_SERVER}` (or similar path)
  - `git pull origin staging`
  - `docker build` and `docker run` with env vars such as `SPRING_PROFILES_ACTIVE=staging`, `SPRING_CLOUD_CONFIG_URI`, `SPRING_KAFKA_BOOTSTRAP_SERVERS`.

### Production deploy (`deploy.yml`)

- **Triggers:** Push/PR to `main` in many workflows.
- **Environment:** GitHub `environment` (e.g. `main`) for approval gates — verify branch protection and required reviewers.
- **SSH user:** May differ from staging (e.g. non-root user vs `root`) — see individual workflow.

### GitHub secrets (typical names)

| Secret | Purpose |
|--------|---------|
| `SERVER_IP` | Target host for deployment / config resolution |
| `PORT` | Published container port |
| `SSH_PRIVATE_KEY` | Private key for deployment SSH |
| `FOLDER_NAME_IN_SERVER` | Repo checkout path on server |
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka brokers (staging) |

Auditors should request a **Secrets inventory** from admins (GitHub org/repo settings are not visible in code).

### Per-repository workflow alignment

Each microservice repo owns its workflow files. **Verify** that `SPRING_APPLICATION_NAME_SUFFIX`, Docker image name, and `FOLDER_NAME_IN_SERVER` match the intended service on the server — mismatches cause wrong containers or paths to update.

### Security controls to verify

- **Least privilege:** SSH keys scoped to deploy user; no shared personal keys.
- **Branch protection:** `main` and `staging` require reviews; prevent force-push where policy demands.
- **Supply chain:** Pin GitHub Actions to commit SHAs or trusted versions; enable Dependabot where applicable.
- **Docker:** Images run as non-root where possible; scan images for CVEs.
- **Config server:** Ensure production config is not world-readable on the host filesystem.

## Proto documentation publishing (afefe-entities)

- Workflow **Deploy Proto Docs to GitHub Pages** builds `public/docs/index.html` and deploys to **GitHub Pages** (`environment: github-pages`). Confirm repository visibility and whether generated docs expose sensitive API details.

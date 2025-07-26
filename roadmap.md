# ExpensiveManager Roadmap Status (2025-07)

## 1. Requirements & Design
- [x] Requirements documented
- [x] ER diagrams & architecture (basic)
- [x] Initial API contract
- [x] Git repo initialized

## 2. Core Monolithic Implementation
- [x] Expense CRUD
- [x] Expense API tests (manual/Postman)
- [x] Project builds and runs
- [x] Category CRUD (completed)
- [x] User CRUD/auth (registration, login, JWT-based security completed)
- [ ] Group/split expense
- [>] Input validation (expand DTOs/annotations)
- [x] Database transactions (basic)
- [ ] Analytics/reporting
- [ ] Seed/test data
- [x] Unit/integration tests (Expense + User/JWT covered)
- [>] API docs (Swagger, expand to all endpoints)
- [>] README with setup/run instructions (update as you go)

## 3. Advanced Features
- [ ] Global exception handling
- [x] Logging (SLF4J basic)
- [ ] Monitoring (Actuator etc.)
- [ ] Multi-threaded reporting/caching/audit
- [>] Complete API docs (Swagger: expand coverage)

## 4. Microservices Refactoring
- [ ] Notification microservice (future phase)
- [ ] Inter-service comm/Docker Compose
- [ ] API Gateway/JWT per service/Separate DBs

## 5. Cloud Deployment
- [ ] Dockerfiles/cloud deploy/docs

## 6. Finalization & Best Practices
- [ ] CI/CD, code review, interview notes, polish

---

**2025-07-26:**
- User registration, login, and JWT-based authentication implemented and tested (manual + unit tests)
- Category CRUD completed and tested
- Auth endpoints exposed and secured in SecurityConfig
- JWT utility and authentication filter documented and refactored
- Postman/cURL test samples prepared for registration and login

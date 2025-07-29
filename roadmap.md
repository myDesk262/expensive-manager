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

Recommended Next Steps
Here’s what’s typically next for a professional backend project like yours, based on your roadmap and industry best practices:

1. Group/Split Expense Feature
Implement support for expense groups (e.g., family, friends, office).

Allow users to split an expense among group members.

Add models, repositories, services, and controller endpoints for groups and split logic.

2. Input Validation & DTO Expansion
Add validation annotations (e.g., @NotNull, @Email, @Min) to your DTOs/entities.

Create error responses for invalid input.

Test with invalid/missing fields in Postman.

3. Analytics & Reporting
Implement API endpoints for expense reports, summaries, and analytics.

Examples: Monthly expense, category-wise totals, top spenders, etc.

4. Seed/Test Data
Create seeders or SQL scripts to populate the database with sample data.

Useful for demos and QA.

5. API Documentation
Expand Swagger/OpenAPI documentation for all endpoints.

Add usage examples and response schemas.

6. Global Exception Handling
Implement a @ControllerAdvice to handle exceptions and return friendly error messages.

Map validation errors, not found, unauthorized, etc.

7. (Optional) UI or Frontend Integration
If you plan to build a frontend, start creating API contracts and/or connect to your APIs.

8. (Ongoing) Refactor and Polish
Refactor code for clarity, reuse, and best practices.

Improve logging, monitoring, and security as needed.

Where to Start?
If following the logical flow of a real-world project, implementing Group/Split Expense and input validation would be the next key features.

## Example Immediate Next Step
Step 1: Design the group & split expense feature:

What is a group?

How is an expense split?

What are the DB relationships?

What APIs are needed (create group, add user to group, add group expense, split logic)?

I can help you design the ER diagram, model, and REST endpoints for this.

## How We’ll Proceed (Professional Flow):
## Step 1: Requirements & Use Cases
Define what a Group is (users, group name, etc.).

Define how splitting works (equal, custom percentages/shares, settle-up logic?).

Sketch a simple ER diagram for relationships: User, Group, Expense, GroupMember, Split.

## Step 2: API Design
Endpoints for:

Creating a group

Adding/removing members

Adding group expenses

Fetching group expenses/splits

## Step 3: Model/Entity Design
New entities (tables): Group, GroupMember, maybe SplitDetail.

Define relationships (many-to-many: users ↔ groups, expenses ↔ groups).

## Step 4: Implement Backend
Create entities, repositories, services, and controllers.

Focus on core logic (splitting expenses and assigning shares).

## Step 5: Testing
Unit and integration tests for group and split logic.

Postman/cURL samples for all endpoints.

## Step 6: Update Docs/Roadmap

----------------------------------------------------------------------------------

1. What is a Group?
A Group is a collection of users (e.g., "Roommates", "Office Trip 2025", "Family").

A user can belong to multiple groups.

Each group has a name and a list of members.

2. What is a Split Expense?
An Expense can be assigned to a group (instead of just one user).

The total expense is split among group members—either equally or by custom shares/percentages.

3. Typical Use Cases
Create a group

User creates a group and invites other users by username or email.

Add/remove members

Members can join or leave the group.

Add a group expense

Any group member can add an expense assigned to the group.

Option to choose equal or custom split.

View group expenses & settlements

See who paid, who owes whom, and settle up balances.

4. Example ER Diagram (Text Version)
text
Copy code
User
 └──< GroupMember >──┐
                     |
                  Group
                     |
              < GroupExpense >
                     |
                 Expense
Entity Suggestions:

User (already exists)

Group (id, name)

GroupMember (id, group_id, user_id)

Expense (already exists, add group_id as optional)

(Optional) SplitDetail for custom shares (expense_id, user_id, share)

5. Example APIs (Initial Brainstorm)
POST /groups → Create a group

POST /groups/{id}/members → Add member to group

DELETE /groups/{id}/members/{userId} → Remove member

POST /groups/{id}/expenses → Add expense to group

GET /groups/{id}/expenses → Get all group expenses

GET /groups/{id}/settlements → Get current split/balances

Questions to Finalize Before Modeling:
Do you want to support both equal and custom splits?
(E.g., Ram pays 700, splits as 200 for A, 200 for B, 300 for Ram.)

Should a user be able to see all their groups and balances?

Do you want invite/join links or just add by username/email?





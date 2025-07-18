User
- id (PK)
- username
- email
- password
- role

Category
- id (PK)
- name
- user_id (FK, nullable: system-level category if null)

Group
- id (PK)
- name
- created_by (FK to User)

GroupMember
- id (PK)
- group_id (FK)
- user_id (FK)
- joined_at

Expense
- id (PK)
- amount
- description
- date
- user_id (FK: paid by)
- category_id (FK)
- group_id (FK, nullable)
- created_at

ExpenseSplit
- id (PK)
- expense_id (FK)
- paid_by (FK User)
- paid_for (FK User)
- amount

AuditLog (optional)
- id (PK)
- entity
- operation
- user_id (FK)
- timestamp

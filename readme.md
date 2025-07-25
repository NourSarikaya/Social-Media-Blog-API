📱 Social Media Blog API
This project is a RESTful backend service for a hypothetical social media micro-blogging platform. It handles user account management and message operations such as creation, retrieval, updating, and deletion. The backend is designed following a 3-layer architecture (Controller, Service, DAO) and persists data using a relational database.

💡 Project Overview
This API provides the core functionality for a micro-blogging application where:

Users can register and log in.

Messages (short text posts) can be created, updated, retrieved, or deleted.

Users can view all messages or messages posted by a specific user.

🗃️ Database Schema
Two primary tables are used:

Account
Column	Type	Description
account_id	INTEGER (PK)	Auto-incremented ID
username	VARCHAR(255)	Must be unique
password	VARCHAR(255)	

Message
Column	Type	Description
message_id	INTEGER (PK)	Auto-incremented ID
posted_by	INTEGER (FK)	Foreign key referencing account_id
message_text	VARCHAR(255)	Non-empty, max 255 characters
time_posted_epoch	LONG	Epoch timestamp of post creation

🧩 API Endpoints
🔐 User Registration
POST /register
Creates a new user account.
Conditions: Username must not be blank; password must be ≥ 4 characters; username must be unique.
Response: JSON of the created account.

🔑 User Login
POST /login
Authenticates a user.
Conditions: Must match an existing username and password.
Response: JSON of the authenticated account.

📝 Create Message
POST /messages
Creates a new message.
Conditions: Message text must be non-empty and ≤ 255 characters; user must exist.
Response: JSON of the created message.

📥 Get All Messages
GET /messages
Returns a list of all messages.
Response: JSON list of messages.

🔍 Get Message by ID
GET /messages/{message_id}
Retrieves a specific message by ID.
Response: JSON of the message or empty if not found.

🗑️ Delete Message
DELETE /messages/{message_id}
Deletes a message by ID.
Response: JSON of deleted message or empty if not found.

✏️ Update Message
PATCH /messages/{message_id}
Updates the text of a message.
Conditions: Message must exist; new text must be valid (non-empty and ≤ 255 characters).
Response: JSON of updated message.

👤 Get Messages by User
GET /accounts/{account_id}/messages
Retrieves all messages posted by a specific user.
Response: JSON list of user messages.

🛠️ Tech Stack
Java

JDBC

SQL

Maven

RESTful API

Three-layer architecture (Controller → Service → DAO)


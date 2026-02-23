Deadline: March 9th (2 weeks)

_**Instructions (From the Professor)**_
1. Task
Implement the assigned User Interface project, preferably, in Java programming
language. Pick or generate your own Backend Database. All components on
your User Interface should be functional with your Backend Database.

2. Deliverables
(a) Program (with all required modules) should run and be bugs free with your
input (database).
(b) Detailed Project Report.

3. Project Team
All the team members are equally contributing to the successful completion of the
project. Grading is solely based on simplicity, functionality, sophistication and the
user friendliness of the interface. No collaboration is allowed between groups.

_**Plan:**_

**1) Tech Stack**

Language: Java

UI: JavaFX (desktop GUI) (kinda annoying we have to use java for ui but it should be ok)

Database: MongoDB (local MongoDB)

DB Access: MongoDB Java Driver

**2) Core Goals**

Fast + accurate results: votes stored immediately; results computed via aggregation query

Anonymity: ballots stored without any voter identity attached

Auditable trail: append-only audit events for key actions (election created, token used, election closed, results viewed)

Reliability: prevent double voting using token usage enforcement

**3) Features (mvp (minimum viable product) + decent ux)**

Admin Flow

-Create election (name + optional start/end)

-Add candidates/options

-Generate voting tokens (N tokens)

-Open/Close election

-View results (counts per candidate)

-View audit log (table)

Voter Flow

-Enter token

-See ballot (candidates list)

-Cast vote (one-time)

-Confirmation screen (no vote receipt that reveals choice)

**4) Database Design (MongoDB)**

elections

{ _id, name, status: OPEN/CLOSED, startAt, endAt, createdAt }

candidates

{ _id, electionId, name, politicalParty }

tokens

{ _id, electionId, tokenHash, used: boolean, usedAt }

ballots

{ _id, electionId, candidateId, castAt }
(no voter identity should be stored in the ballots db to keep voting anonymous)

audit_events

{ _id, electionId, type, timestamp, meta }

Double-vote prevention

Voting uses an atomic “mark token used if unused” operation:

If token already used → reject vote

If token unused → mark used + insert ballot + insert audit event

**5) Work Split**

_Zakir — Backend/Data Layer (Mongo + correctness)_

Owns:

MongoDB setup + collections + indexes

Java DB connection module

Repository/DAO classes for each collection

Service methods that enforce voting rules (especially the atomic vote transaction)

Aggregation query for results

Seed data + token generator

Unit/basic tests + a CLI test harness if needed

Deliverables:

Working database + scripts/instructions

DAO/Service layer code

Verified “cannot vote twice” behavior

Results query works

_Nicholas — JavaFX UI/UX (user-friendliness + polish)_

Owns:

All JavaFX screens + navigation

Input validation + user feedback messages

Wiring UI actions to Service layer calls

Tables for results + audit log

Presentation-ready flow and usability

Screens to implement:

Home (Admin / Voter)

Admin Login (simple password or local credential)

Admin Dashboard

Create election

Add candidates

Generate tokens

Open/Close election

View Results

View Audit Log

Voter Token Entry

Voting Screen (radio select + submit)

Confirmation / Error screens

(I can help you out if you need it just feel free to text me on discord, it's a lotta work lol)

**6) Shared “Contract” (to avoid integration issues)**

We agree on service method signatures early so UI and DB can build in parallel:

createElection(name, startAt, endAt)

addCandidate(electionId, name)

generateTokens(electionId, count) -> List<String> (raw tokens)

openElection(electionId)

closeElection(electionId)

getActiveElection()

getCandidates(electionId)

castVote(rawToken, electionId, candidateId)

getResults(electionId) -> (candidateName, count)

getAuditEvents(electionId)

UI calls Service layer only (doesn’t talk to Mongo directly).
We keep the method signatures the same for readability

**7) Milestones / Timeline (simple sprint plan)**

Milestone 1: Design + Setup

Finalize UI screen list + DB schema

Repo created with folders + basic JavaFX app skeleton

Mongo connection working

Milestone 2: Core Voting Works

Admin can create election + candidates

Tokens generated

Voter can submit vote once (double-vote blocked)

Milestone 3: Results + Audit

Results aggregation works (counts per candidate)

Audit log table works

Close election blocks voting

Milestone 4: Polish + Bug-Free Demo

Validation, clear error handling, UI cleanup

Test scenarios + demo script

Report screenshots + final packaging

**8) Testing Checklist (what we will demo)**

Create election, add candidates

Generate tokens and copy one token

Vote with token → success

Vote again with same token → rejected

View results (counts correct)

Close election → voting disabled

Audit log shows actions in timestamp order

**9) Report Outline (decent project report)**

Overview + requirements mapping (speed, audit, anonymity)

Architecture diagram (JavaFX → Service → DAO → MongoDB)

Database schema/collections + indexes

UI screenshots + user flow

Algorithms/logic:

token-based anonymity

atomic vote casting

results aggregation

Testing + known limitations + future improvements





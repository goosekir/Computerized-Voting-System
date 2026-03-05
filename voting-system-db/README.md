# voting-system-db (handoff)

## What this folder contains
- `schema.txt` — collections + fields used
- `indexes.txt` — required indexes (includes double-vote protection)
- `results_query.txt` — aggregation pipeline used for election results
- `validators/` — schema validation JSON used in Compass
- `proof/` — screenshots proving "cannot vote twice" + results output

## MongoDB connection info
- URI: `mongodb://localhost:27017`
- Database: `voting_system`
- Collections: `elections`, `candidates`, `tokens`, `votes`

## Required DB Rules (contract)
### Votes
- A token cannot vote twice in the same election.
- Enforced by unique index on: `(electionId, tokenHash)`
- Vote document fields:
  - `electionId` (ObjectId)
  - `tokenHash` (string)
  - `candidateId` (ObjectId)
  - `createdAt` (date)

### Tokens
- Token uniqueness enforced by unique index on: `(electionId, tokenHash)`
- Token fields:
  - `electionId` (ObjectId)
  - `tokenHash` (string)
  - `status` (ACTIVE | USED | REVOKED)
  - `issuedAt` (date)
  - `usedAt` (date or null)

## Results query
Use the pipeline in `results_query.txt` (Mongo aggregation). Verified in Compass.

## Setup
# Requirements

You must have:

- MongoDB Community Server
- MongoDB Shell (mongosh)

------------------------------------------------------------------------

## 1. Install MongoDB Shell (mongosh)

Download MongoDB Shell:

https://www.mongodb.com/try/download/shell

Choose:

Platform: **Windows**\
Package: **ZIP**

Extract it somewhere on your computer.

Example location:

    C:\Users\YOURNAME\Downloads\mongosh-2.7.0-win32-x64\bin

------------------------------------------------------------------------

## 2. Verify mongosh works

PowerShell requires a **call operator `&`** when running executables
inside quotes.

Run:

``` powershell
& "C:\Users\YOURNAME\Downloads\mongosh-2.7.0-win32-x64\bin\mongosh.exe" --version
```

Expected output:

    2.7.0

If you see that version number, mongosh is installed correctly.

------------------------------------------------------------------------

## 3. Navigate to the project scripts

``` powershell
cd C:\Users\YOURNAME\Downloads\voting-system-db\scripts
```

------------------------------------------------------------------------

## 4. Initialize the database

``` powershell
& "C:\Users\YOURNAME\Downloads\mongosh-2.7.0-win32-x64\bin\mongosh.exe" "mongodb://127.0.0.1:27017" --file init.js
```

This creates the following collections:

    elections
    candidates
    tokens
    votes

It also applies schema validation rules and indexes.

------------------------------------------------------------------------

## 5. Seed the database

``` powershell
& "C:\Users\YOURNAME\Downloads\mongosh-2.7.0-win32-x64\bin\mongosh.exe" "mongodb://127.0.0.1:27017" --file seed.js
```

Example output:

    Election created: Demo Election
    electionId: ...

    Candidates:
    Alice
    Bob

    Inserted 20 tokens
    Auto-cast 8 votes

The script will also print **remaining ACTIVE raw tokens** for testing.

------------------------------------------------------------------------

# Verify database

You can confirm everything worked by running:

``` bash
mongosh "mongodb://localhost:27017/voting_system" --eval "db.votes.countDocuments()"
```

Expected output:

    8

------------------------------------------------------------------------
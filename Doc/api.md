# Application programming interface for Rest-Voting-App

    Version 1.0
    
    Root path: http://localhost:8080/rest-voting-app

### Table of context
#### 1. Description
#### 2. Privileges
#### 3. Endpoints
#####     3.1. Users

3.1.1. [Authentication](3_1_1.md)

3.1.2. [Get users list](3_1_2.md)

3.1.3. [Get user](3_1_3.md)

3.1.4. [Create user](3_1_4.md)

3.1.5. [Update user](3_1_5.md)

3.1.6. [Delete user](3_1_6.md)

#####     3.2. Restaurants

3.2.1. [Get restaraunts list](3_2_1.md)
  
3.2.2. [Get active restaraunts list](3_2_2.md)

3.2.3. [Get restaurant](3_2_3.md)

3.2.4. [Create restaurant](3_2_4.md)

3.2.5. [Update restaurant](3_2_5.md)

3.2.6. [Delete restaurant](3_2_6.md)

#####     3.3. Menus

3.3.1. [Get menus list by date](3_3_1.md)

3.3.2. [Get menus list by date and restaurant](3_3_2.md)

3.3.3. [Get menu](3_3_3.md)

3.3.4. [Create menu](3_3_4.md)

3.3.5. [Update menu](3_3_5.md)

3.3.6. [Delete menu](3_3_6.md)

#####     3.4. Votes

3.4.1. [Get votes list by date](3_4_1.md)

3.4.2. [Get count of votes by date](3_4_2.md)

3.4.3. [Get count of votes by date and restaurant](3_4_3.md)

3.4.4. [Create vote](3_4_4.md)

3.4.5. [Delete vote](3_4_5.md)


## Description
The service provides the facilities of editing users, restaurants, menus and voting. 
Admin manages the users list and user rights.
Also Admin provides actual restaurants list and menus. 
User decides: On which restaurant are you want to have lunch at today.
Authorized user can put their vote to a single restaurant per day. 
User can change their mind till 11:00 am today.
Results of the voting can be view.

## Privileges 
The service has two roles:
* ADMIN
* USER

#### Summary table of resources
**Root path: /rest-voting-app**

Method | URL                             | Auth | Roles
-------|---------------------------------|------|------------
POST   | users/login                     |      | 
GET    | users?page={page}&limit={limit} | +    | ADMIN
GET    | users/{id}                      | +    | ADMIN
POST   | users                           | +    | ADMIN
PUT    | users/{id}                      | +    | ADMIN
DELETE | users/{id}                      | +    | ADMIN
GET    | restaurants                     | +    | ADMIN, USER
GET    | restaurants/active              | +    | ADMIN, USER
GET    | restaurants/{id}                | +    | ADMIN, USER
POST   | restaurants                     | +    | ADMIN
PUT    | restaurants/{id}                | +    | ADMIN
DELETE | restaurants/{id}                | +    | ADMIN
GET    | menus/date/{date}               | +    | ADMIN, USER
GET    | menus/date/{date}/restaurant/{restaurantId} | +    | ADMIN, USER
GET    | menus/{id}                      | +    | ADMIN, USER
POST   | menus                           | +    | ADMIN
PUT    | menus/{id}                      | +    | ADMIN
DELETE | menus/{id}                      | +    | ADMIN
GET    | votes/date/{date}?page={page}&limit={limit} | +    | ADMIN, USER
GET    | votes/date/{date}/count         | +    | ADMIN, USER
GET    | votes/date/{date}/count/restaurant/{restaurantId} | +    | ADMIN, USER
POST   | votes                           | +    | ADMIN, USER
DELETE | votes/{id}                      | +    | ADMIN, USER
----
Next: 3.1.1 [Authentication](3_1_1.md)

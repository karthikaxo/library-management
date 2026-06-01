## Library Management System
A backend REST API for managing a library system built with Java and Spring Boot. The 
application handles core library operations such as library, account (members and librarians) 
and book management, book lending, returns, and fine handling for lost books. It also includes 
secure, role-based authorization using Spring Security, 
ensuring that only certain roles can access and perform sensitive operations.


### Relationship Diagram
<img src="repo%20imgs/relationship%20diagram.png" alt="relationship" width="800">


### Current Endpoints

| Method | Endpoint                                  | Role access                  | Description                                                                    |
|--------|-------------------------------------------|------------------------------|--------------------------------------------------------------------------------|
| POST   | `/library/create`                         | ```ADMIN```                  | Create a new library                                                           |
| GET    | `/library/search/{id}`                    | ```ADMIN```                  | Retrieve a library by ID                                                       |
| GET    | `/library/search`                         | ```ADMIN```                  | Retrieves libraries using filters: name and address                            |
| PATCH  | `/library/update/{id}`                    | ```ADMIN```                  | Update an existing library                                                     |
| DELETE | `/library/delete/{id}`                    | ```ADMIN```                  | Delete a library                                                               |
| POST   | `/account/create-lib`                     | ```ADMIN```                  | Create a user with role ```LIBRARIAN```.                                       |
| POST   | `/account/create-mem`                     | No auth                      | Create a user with role ```MEMBER```.                                          |
| POST   | `/account/login`                          | No auth                      | Login with username and password. Returns JWT token.                           |
| GET    | `/account/search-mem/id/{id}`             | ```ADMIN```,```LIBRARIAN```  | Search for a member by their ID                                                |
| GET    | `/account/search-lib/id/{id}`             | ```ADMIN```                  | Search for a librarian by their ID                                             |
| GET    | `/account/search-mem/username/{username}` | ```ADMIN```,```LIBRARIAN```  | Search for a member by their username                                          |
| GET    | `/account/search-lib/username/{username}` | ```ADMIN```                  | Search for a librarian by their username                                       |
| GET    | `/account/search-mem/email/{email}`       | ```ADMIN```, ```LIBRARIAN``` | Search for a member by their email                                             |
| GET    | `/account/search-lib/email/{email}`       | ```ADMIN```                  | Search for a librarian by their email                                          |
| GET    | `/account/search-mem`                     | ```ADMIN```,```LIBRARIAN```  | Retrieves members using filters: username, createDate, lastLogin               |
| GET    | `/account/search-lib`                     | ```ADMIN```                  | Retrieves librarians using filters: libraryID, username, createDate, lastLogin |
| PATCH  | `/account/update-mem/{id}`                | ```ADMIN```,```MEMBER```     | Update an existing member                                                      |
| PATCH  | `/account/update-lib/{id}`                | ```ADMIN```,```LIBRARIAN```  | Update an existing librarian                                                   |
| PATCH  | `/account/update-pw-mem/{id}`             | ```ADMIN```,```MEMBER```     | Update an existing member's password                                           |
| PATCH  | `/account/update-pw-lib/{id}`             | ```ADMIN```,```LIBRARIAN```  | Update an existing librarian's password                                        |
| DELETE | `/account/delete/{id}`                    | ```ADMIN```                                | Delete an account                                                              |
| POST   | `/book/create`                            | ```ADMIN```,```LIBRARIAN```               | Create a new book                                                              |
| GET    | `/book/search/id/{id}`                    | ```ADMIN```,```LIBRARIAN```, ```MEMBER``` | Retrieve a book by ID                                                          |
| GET    | `/book/search/isbn/{isbn}`                | ```ADMIN```,```LIBRARIAN```, ```MEMBER```                  | Retrieve a book by ISBN                                                        |
| GET    | `/book/search`                            | ```ADMIN```,```LIBRARIAN```, ```MEMBER```                  | Retrieves books using filters: title, author, category,year                    |
| PATCH  | `/book/update/{id}`                       | ```ADMIN```,```LIBRARIAN```                                | Update an existing book                                                        |
| DELETE | `/book/delete/{id}`                       | ```ADMIN```,```LIBRARIAN```                                | Delete a book                                                                  |
| POST   | `/bookitem/create`                        | ```ADMIN```,```LIBRARIAN```                                | Create a new book item                                                         |
| GET    | `/bookitem/search/id/{id}`                | ```ADMIN```,```LIBRARIAN```                                | Retrieve a book item by ID                                                     |
| GET    | `/bookitem/search/barcode/{barcode}`      | ```ADMIN```,```LIBRARIAN```                                | Retrieve a book item by barcode                                                |
| GET    | `/bookitem/search`                        | ```ADMIN```,```LIBRARIAN```                                | Retrieves books using filters: status, date and barcode                        |
| PATCH  | `/bookitem/update/{id}`                   | ```ADMIN```,```LIBRARIAN```                                  | Update an existing book item                                                   |
| DELETE | `/bookitem/delete/{id}`                   | ```ADMIN```,```LIBRARIAN```                                  | Delete a book item                                                             |
| POST   | `/lending/create`                         | ```ADMIN```,```LIBRARIAN```               | Create a new lending record                                                    |
| GET    | `/lending/search/id/{id}`                 | ```ADMIN```,```LIBRARIAN``` | Retrieve a lending record by ID                                                |
| GET    | `/lending/search/accountID/{accountID}`   | ```ADMIN```,```LIBRARIAN``` | Retrieve lending records by member ID, and optional filter: lending status     |
| GET    | `/lending/search/overdue`                 | ```ADMIN```,```LIBRARIAN``` | Retrieve all overdue lendings                                                  |
| PATCH  | `/lending/return/{id}`                    | ```ADMIN```,```LIBRARIAN```                                  | Update lending status when book is returned                                    |
| PATCH  | `/lending/lost/{id}`                      | ```ADMIN```,```LIBRARIAN```                                  | Update lending status when book is lost                                        |
| DELETE | `/lending/delete/{id}`                    | ```ADMIN```,```LIBRARIAN```                                  | Delete a lending record                                                        |
| GET      | `/fine/search/{id}`                       | ```ADMIN```,```LIBRARIAN```                                  | Retrieve fine by ID                                                            |
| PATCH  | `/fine/paid/{id}`                         | ```ADMIN```,```LIBRARIAN```                                  | Update fine status when paid off                                               |

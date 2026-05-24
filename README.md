### Current Endpoints

| Method | Endpoint               | Role access                               | Description                                                 |
|--------|------------------------|-------------------------------------------|----------------------------------------------------|
| POST   | `/library/create`      | ```ADMIN```                               | Create a new library                                        |
| GET    | `/library/search/{id}` | ```ADMIN```                               | Retrieve a library by ID                                    |
| GET    | `/library/search`      | ```ADMIN```                               | Retrieves libraries using filters: name and address         |
| PATCH  | `/library/update/{id}` | ```ADMIN```                               | Update an existing library                                  |
| DELETE | `/library/delete/{id}` | ```ADMIN```                               | Delete a library                                            |
| POST   | `/account/create-lib`  | ```ADMIN```                               | Create a user with role ```LIBRARIAN```.                    |
| POST   | `/account/create-mem`  | No auth                                   | Create a user with role ```MEMBER```.                          |
| POST   | `/account/login`       | No auth                                   | Login with username and password. Returns JWT token.        |
| POST   | `/book/create`                 | ```ADMIN```,```LIBRARIAN```               | Create a new book                                           |
| GET    | `/book/search/id/{id}`         | ```ADMIN```,```LIBRARIAN```, ```MEMBER``` | Retrieve a book by ID                                       |
| GET    | `/book/search/isbn/{isbn}`     | ```ADMIN```,```LIBRARIAN```, ```MEMBER```                  | Retrieve a book by ISBN                                     |
| GET    | `/book/search`                 | ```ADMIN```,```LIBRARIAN```, ```MEMBER```                  | Retrieves books using filters: title, author, category,year |
| PATCH  | `/book/update/{id}`            | ```ADMIN```,```LIBRARIAN```                                | Update an existing book                                     |
| DELETE | `/book/delete/{id}`            | ```ADMIN```,```LIBRARIAN```                                | Delete a book                                               |
| POST   | `/bookitem/create`             | ```ADMIN```,```LIBRARIAN```                                | Create a new book item                                      |
| GET    | `/bookitem/search/id/{id}`     | ```ADMIN```,```LIBRARIAN```                                | Retrieve a book item by ID                                  |
| GET    | `/bookitem/search/barcode/{barcode}` | ```ADMIN```,```LIBRARIAN```                                | Retrieve a book item by barcode                             |
| GET    | `/bookitem/search`             | ```ADMIN```,```LIBRARIAN```                                | Retrieves books using filters: status, date and barcode     |
| PATCH  | `/bookitem/update/{id}`        | ```ADMIN```,```LIBRARIAN```                                  | Update an existing book item                                |
| DELETE | `/bookitem/delete/{id}`        | ```ADMIN```,```LIBRARIAN```                                  | Delete a book item                                          |
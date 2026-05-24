### Current Endpoints

| Method | Endpoint               | Description                                                 |
|--------|------------------------|-------------------------------------------------------------|
| POST   | `/library/create`      | Create a new library                                        |
| GET    | `/library/search/{id}` | Retrieve a library by ID                                    |
| GET    | `/library/search`      | Retrieves libraries using filters: name and address         |
| PATCH  | `/library/update/{id}` | Update an existing library                                  |
| DELETE | `/library/delete/{id}` | Delete a library                                            |
| POST   | `/account/create-lib`  | Create a user with role ```LIBRARIAN```.                    |
| POST   | `/account/create-mem`  | Create a user with role ```MEMBER```.                          |
| POST   | `/account/login`       | Login with username and password. Returns JWT token.        |
| POST   | `/book/create`                 | Create a new book                                           |
| GET    | `/book/search/id/{id}`         | Retrieve a book by ID                                       |
| GET    | `/book/search/isbn/{isbn}`     | Retrieve a book by ISBN                                     |
| GET    | `/book/search`                 | Retrieves books using filters: title, author, category,year |
| PATCH  | `/book/update/{id}`            | Update an existing book                                     |
| DELETE | `/book/delete/{id}`            | Delete a book                                               |
| POST   | `/bookitem/create`             | Create a new book item                                      |
| GET    | `/bookitem/search/id/{id}`     | Retrieve a book item by ID                                  |
| GET    | `/bookitem/search/barcode/{barcode}` | Retrieve a book item by barcode                             |
| GET    | `/bookitem/search`             | Retrieves books using filters: status, date and barcode     |
| PATCH  | `/bookitem/update/{id}`        | Update an existing book item                                |
| DELETE | `/bookitem/delete/{id}`        | Delete a book item                                          |
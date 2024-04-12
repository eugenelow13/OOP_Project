# OOP_Project

### Running the project
#### Prerequisites
- Ensure Docker Daemon is runnning

#### Running the project
- Go to the backend (`cd backend` directory and run the following command):

On windows:
```bash
mvn.cmd spring-boot:run
```
On Unix-based systems:
```bash
./mvnw spring-boot:run
```

- Go back to the root directory
- Go to the oop-project (`cd oop-project` directory and run the following command):

```npm
npm install
npm run serve
```
#### Seeding data
- Go to the backend directory and run the following command:

```bash
docker exec -i backend-mysql-1  mysql -u root --password=verysecret < deploy.sql
```

### Technologies used (non-exhaustive list)
#### Backend
- Java
- Spring MVC (for creating RESTful APIs)
- Spring Data JPA
- Spring Security (For JWT Authentication)
- Apache POI (for writing Excel files)
- iText (for generating PDFs)
- Lombok (for generating getters, setters, constructors, etc.)
- Maven (for dependency management)
- MySQL (for the database)
- Docker
- Docker Compose
#### Frontend
- Vue.js
- Vuetify
- Vue Router

### Authors

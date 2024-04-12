# OOP_Project

## Course and Team Information
### IS442-Object Oriented Programming, AY2023/24 T2


### Running the project
#### Prerequisites
- Ensure Docker Daemon is runnning

#### Running the Backend Server
- Go to the backend (cd backend directory and run the following command):

On windows:
```sh
mvn.cmd spring-boot:run
```

On Unix-based systems:
```sh
./mvnw spring-boot:run
```
#### Running the Frontend Server
- Go back to the root directory
- Go to the oop-project (cd oop-project directory and run the following command):

```sh
npm install
npm run serve
```

#### Seeding data
- Go to the backend directory and run the following command:

docker exec -i backend-mysql-1  mysql -u root --password=verysecret < deploy.sql

#### Loaded Homepage
- Copy any of the URL in the Frontend 'terminal' and launch it at the browser of your choice
- The homepage of the application will be loaded as follows
![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/homepage.jpg)

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
- Sean Lim
- Low Jun Jie Eugene
- Bryan Low Chee Theng
- Nicholas Arvin Singham
- Chua Wei Ling Jacqueline
- Luqman Juzaili Bin Muhammad Najib

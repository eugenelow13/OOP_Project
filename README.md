# OOP_Project

## Course and Team Information
### IS442-Object Oriented Programming, AY2023/24 T2


### Running the project
#### Prerequisites
- Ensure Docker Daemon is runnning

![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/docker-daemon-running.jpg)

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

- The terminal after running the commands will look like this:

![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/backend1.jpg)

#### Seeding data
- Go to the backend directory and run the following command:

```sh
docker exec -i backend-mysql-1  mysql -u root --password=verysecret < deploy.sql
```


- The terminal after running the commands will look like this:

![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/sqlcommand.jpg)


#### Running the Frontend Server
- Go back to the root directory
- Go to the oop-project (cd oop-project directory and run the following command):

```sh
npm install
npm run serve
```

- The terminal after running the commands will look like this:
  - Localhost: http://localhost:8082/

![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/frontend-terminal.jpg)



#### Loaded Homepage
- Copy any of the URL in the Frontend 'terminal' and launch it at the browser of your choice:

![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/frontend-link.jpg)

- The homepage of the application will be loaded as follows:

![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/homepage.jpg)

#### Existing Accounts 

- Here are some user mock up data that contains 3 Accounts from the different roles (Customer, Event Manager, Ticketing Officer). 

    | User_ID | Name | Email | Password | Roles |
    |----------|----------|----------| ----------| ----------|
    |1 | John Doe | john.doe@example.com | password | Customer |
    |2 | Jane Doe | jane.doe@example.com| password |  Event Manager |
    |3 | JaneJohn Doe | janejohn.doe@example.com | password|  Tickering Officer |

#### Login Page
- At the login page, key in the account credentials (e.g., email and password) to visit the different pages, based on the user role


- At the homepage, click on the 'Login/Signup'
![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/homepage2.jpg)

- You will arrive at the login page. Key in the credentials depending on the user type of your choice.
![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/login.jpg)

##### Customer Homepage

- If you are logged in as a 'Customer', this will be the homepage.

![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/cus_homepage1.jpg)

![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/cus_homepage2.jpg)


##### Event Manager Homepage

- If you are logged in as a 'Event Manager', this will be the homepage.

![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/em_homepage.jpg)


#####  Homepage

- If you are logged in as a 'Ticketing Officer', this will be the homepage.

![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/to_homepage1.jpg)

![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/to_homepage2.jpg)




#### Register with a valid email address
- Fill up the registration details with a valid email address. This will ensure that bookings placed for the account will have the booking information sent to a working email address. 

- From the Login Page, click on 'Register'

![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/login-circled.jpg)


- Fill in the registration details, with a valid email and click 'Register'

![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/register_full_1.jpg)



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

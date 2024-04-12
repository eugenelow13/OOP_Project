# OOP_Project

## Course and Team Information
### IS442-Object Oriented Programming, AY2023/24 T2


### Running the project
#### Prerequisites
- Ensure Docker Daemon is runnning

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/docker_daemon_running.jpg)

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

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/backend1.jpg)

#### Seeding data
- Go to the backend directory and run the following command:

    ```sh
    docker exec -i backend-mysql-1  mysql -u root --password=verysecret < deploy.sql
    ```

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/sqlcommand.jpg)


#### Running the Frontend Server
- Go back to the root directory
- Go to the oop-project (cd oop-project directory and run the following command):

    ```sh
    npm install
    npm run serve
    ```

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/frontend-terminal.jpg)



#### Loaded Homepage
- Copy any of the URL in the Frontend 'terminal' and launch it at the browser of your choice:
  - Localhost: http://localhost:8082/

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/frontend-link.jpg)

- The homepage of the application will be loaded as follows:

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/homepage.jpg)


#### Register with a valid email address
- Fill up the registration details with a valid email address. This will ensure that email notifications can be sent to the account with the registered email address. 
- Registered account will be automatically be assigned the 'Customer' role.

- From the Login Page, click on 'Register'

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/login-circled.jpg)


- Fill in the registration details, with a valid email and click 'Register'

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/register_full_1.jpg)


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

- If you are logged in as an 'Event Manager', this will be the homepage.

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/em_homepage.jpg)


#####  Homepage

- If you are logged in as a 'Ticketing Officer', this will be the homepage.

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/to_homepage1.jpg)

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/to_homepage2.jpg)




### Appendix

#### Coding Documentation Practices

For services with complex logic computation, we have documented the code with comments, providing specific details that help explain the inner workings of the code in depth. 

- **Example 1:** Email Service

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/sent_email.jpg)



- **Example 2:** Place Booking

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/place_booking.jpg)


- **Example 3:** Cancel Booking

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/cancel_booking.jpg)



#### Querying from Docker MySQL

This section will show how information is stored in the database by interacting with the Docker MySQL interface to obtain details about the tables and to query data through SQL commands.

- In Docker, click on the '>' icon beside the 'backend' container

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/docker_sql_1.jpg)

- In the expanded collapse view, click on 'mysql-1'

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/docker_sql_2.jpg)


- Navigate to the 'exec' tab
- Run the following commands to log in to the database

    ```sh
    mysql -u myuser -p
    password: secret
    ```

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/docker_sql_3.jpg)


- To access the database and view the tables available, run the following commands:

    ```sh
    use mydatabase;
    show tables;
    ```

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/docker_sql_4.jpg)


- To view the schema of a table, run the following command (Replace 'User' with any table of your choice)

    ```sh
    describe user;
    ```

    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/docker_sql_5.jpg)

- To query rows from a table, run the following command (Replace 'User' with any table of your choice)

    ```sh
    select * from user;
    ```


    ![alt text](https://github.com/eugenelow13/OOP_Project/blob/main/screenshots/docker_sql_6.jpg)



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

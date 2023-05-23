Application Setup and Run Guide
Follow these steps to set up and run the application.

Prerequisites
Java Development Kit (JDK) 11 
Apache Maven installed
MySQL,PostgreSQL or H2 database server installed (choose one)


Step 1: Clone the Repository
shell
Copy code
$ git clone https://github.com/your-username/user-auth-service.git
$ cd user-auth-service



Step 2: Configure the Database
Open the src/main/resources/application.properties file.
Set the appropriate database connection properties for H2 db
Save the changes.


Step 3: Build the Application
shell
Copy code
$ mvn clean package
Step 4: Run the Application
shell
Copy code
$ java -jar target/user-auth-service.jar
The application will start running on http://localhost:8080.

Step 5: Access the API Documentation
Open your web browser and go to http://localhost:8080/swagger-ui.html to access the API documentation.
Step 6: Access the User Interface (Optional)
Open your web browser and go to http://localhost:8080 to access the user interface.
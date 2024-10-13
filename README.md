# Quiz Service

The Quiz Service is a Spring Boot application developed for student and quiz management. It uses an H2 database to run
in-memory and supports basic CRUD operations.

## Features

- Student management
- Quiz management
- Question and option management
- Quiz assignments
- Answering and result evaluation
- H2 database console support
- API documentation with Swagger UI

## Technologies

- Java
- Spring Boot
- Spring Cache
- Spring Boot DevTools
- Spring Data JPA
- H2 Database
- Lombok
- Jakarta Validation
- Swagger
- JUnit
- Mockito

## Installation

1. **Requirements**:
    - JDK 17 or higher
    - Maven 3.6 or higher


2. **Clone the project**:
   ```bash
   git clone <repository-url>
   cd quiz-service

3. **Install dependencies**:
   ```bash
   mvn clean install

4. **Run the application**:
   ```bash
   mvn spring-boot:run

5. **Access the H2 Console**:
    - Go to http://localhost:8080/h2-console in your browser.
        - Enter jdbc:h2:mem:quiz as the JDBC URL.
        - Username: admin
        - Password: 1234


6. **Access Swagger UI**:
    - Go to http://localhost:8080/swagger-ui.html in your browser.
    - You can access the API documentation and test it from here.

## API Usage

### Student API

- Get all students: `GET /students`
- Get student by id: `GET /students/{id}`
- Get quiz assignments by student id: `GET /students/{id}/quiz-assignments`
- Get quizzes by student id: `GET /students/{studentId}/quizzes`
- Get completed quizzes for a student: `GET /students/{studentId}/completed-quizzes`
- Create a new student: `POST /students`
- Update a student: `PATCH /students/{id}`
- Delete a student: `DELETE /students/{id}`

### Quiz API

- Get all quizzes: `GET /quizzes`
- Get quiz by id: `GET /quizzes/{id}`
- Get quiz assignments by quiz id: `GET /quizzes/{id}/quiz-assignments`
- Get students by quiz id: `GET /quizzes/{id}/students`
- Get questions by quiz id: `GET /quizzes/{id}/questions`
- Create a new quiz: `POST /quizzes`
- Update a quiz: `PATCH /quizzes/{id}`
- Delete a quiz: `DELETE /quizzes/{id}`

### Question API

- Get a question by id: `GET /questions/{id}`
- Get a question without answer by id: `GET /questions/{id}/without-answer`
- Get options by question id: `GET /questions/{questionId}/options`
- Create a new question: `POST /questions`
- Update a question: `PATCH /questions/{id}`
- Delete a question: `DELETE /questions/{id}`

### Option API

- Get an option by id: `GET /options/{id}`
- Create a new option: `POST /options`
- Update an option: `PATCH /options/{id}`
- Delete an option: `DELETE /options/{id}`

### Quiz Assignment API

- Get all quiz assignments: `GET /quiz-assignments`
- Get answers by quiz assignment id: `GET /quiz-assignments/{quizAssignmentId}/answers`
- Get quiz assignment by id: `GET /quiz-assignments/{id}`
- Create a new quiz assignment: `POST /quiz-assignments`
- Start the quiz: `PATCH /quiz-assignments/{quizAssignmentId}/start`
- Submit an answer for a quiz question: `PATCH /quiz-assignments/answer`
- Complete the quiz: `PATCH /quiz-assignments/{quizAssignmentId}/complete`

## Error Handling

The application utilizes custom error codes to manage exceptions. The following error codes are defined:

- **STUDENT_NOT_FOUND (1001)**: Thrown when the specified student cannot be found in the system.
- **QUIZ_NOT_FOUND (1002)**: Thrown when the specified quiz cannot be located in the system.
- **QUESTION_NOT_FOUND (1003)**: Thrown when the specified question cannot be found in the system.
- **OPTION_NOT_FOUND (1004)**: Thrown when the specified option cannot be found in the system.
- **QUIZ_ASSIGNMENT_NOT_FOUND (1005)**: Thrown when the specified quiz assignment cannot be found in the system.
- **QUIZ_COMPLETED_ALREADY (1006)**: Thrown when an attempt is made to start a quiz that has already been completed.
- **QUIZ_NOT_IN_PROGRESS (1007)**: Thrown when an attempt is made to submit an answer for a quiz that is not currently
  in progress.
- **VALIDATION_ERROR (1008)**: Thrown when validation errors occur using Jakarta Validation.
- **DATA_INTEGRITY_VIOLATION (1009)**: Thrown when a violation of data integrity occurs, such as a unique constraint
  violation

## Data Models

### Student

- Description: Holds student information (id, first name, last name).
- Relationships: Associated with multiple quiz assignments.

### Quiz

- Description: Holds quiz information (name, questions).
- Relationships: Associated with multiple questions and quiz assignments.

### Question

- Description: Holds the question text and the correct answer.
- Relationships: Associated with a quiz and can have multiple options.

### Option

- Description: Holds the options for questions.
- Relationships: Associated with a question.

### Answer

- Description: Holds the answers provided by students.
- Relationships: Associated with a quiz assignment and a question.

### QuizAssignment

- Description: Holds the quiz assignments given to students.
- Relationships: Associated with a student and a quiz; can contain multiple answers.

## Configuration Files

### application.properties

- **spring.application.name**: The name of the Spring application.
- **spring.h2.console.enabled**: Enables the H2 database console for accessing the in-memory database.
- **spring.datasource.url**: The JDBC URL for connecting to the H2 in-memory database.
- **spring.datasource.driverClassName**: The driver class name for H2 database connections.
- **spring.datasource.username**: The username for accessing the H2 database.
- **spring.datasource.password**: The password for accessing the H2 database.
- **spring.jpa.database-platform**: The Hibernate dialect used for the H2 database.
- **spring.cache.type**: The caching strategy used in the application.
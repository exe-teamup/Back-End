**Exe TeamUp (Back-End)**
A modern back-end service built with Java Spring Boot, designed to manage students, courses, and academic group formation.

**Features**
🍃 Spring Boot 3.x
☕️ Java 21 
🗃️ Spring Data JPA (MySQL) 
🔒 Spring Security & JWT 
🔥 Firebase Authentication 
📖 Springdoc (Swagger)  
📊 Apache POI 
🐳 Docker 
🔄 CI/CD 
⚙️ Configuration Management

**Getting Started**
**Prerequisites**
JDK 21
Apache Maven
MySQL Server
Google Firebase Account (for service account credentials file)

**Installation**
Clone the repository:

**Bash**
git clone https://github.com/exe-teamup/Back-End.git
cd Back-End

**Install Maven dependencies:**
**Bash**
mvn install
Configuration
This project uses dotenv-java and Spring Profiles. Create a .env file in the src/main/resources/ directory or set the following environment variables:

SPRING_DATASOURCE_URL (e.g., jdbc:mysql://localhost:3306/exe_teamup_db)
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
TOKEN_SECRET_KEY (A long, random secret for signing JWTs)
FCM_CREDENTIALS_FILE_PATH (Path to your firebase-admin.json credentials file)

**Development**
Run the development server:

**Bash**
mvn spring-boot:run
The application will be available at http://localhost:8080.

**Available Maven Scripts**
mvn spring-boot:run - Start the application in development mode.
mvn clean package - Build the production-ready JAR file (includes tests).
mvn clean package -DskipTests - Build the JAR file without running tests.
mvn test - Run the test suite.

**Project Structure**
src/main/java/com/team/exeteamup/
├── config/              # Spring Security, Firebase, CORS, Swagger
├── controller/          # RESTful API endpoints
├── dto/                 # Data Transfer Objects (Request & Response)
├── entity/              # JPA Database Entities
├── enums/               # Application-wide Enums (Roles, Statuses)
├── exception/           # Global exception handlers
├── mapper/              # MapStruct/manual mappers for DTOs/Entities
├── repository/          # Spring Data JPA Repositories
├── service/             # Business logic (Interfaces & Implementations)
├── utils/               # Utility classes (e.g., UserUtils)
└── ExeTeamupApplication.java # Main Spring Boot application entry point

**API Documentation**
API documentation is auto-generated using Springdoc (Swagger UI). Once the application is running, you can access it at:
http://localhost:8080/api/docs

**Configuration Files**
pom.xml - Maven project dependencies and build configuration.
Dockerfile - Multi-stage Docker build file.
.github/workflows/deploy.yml - GitHub Actions CI/CD pipeline.
src/main/resources/application.yaml - Main Spring Boot configuration.
src/main/resources/application-dev.yaml - Development profile configuration.
src/main/resources/application-prod.yaml - Production profile configuration.

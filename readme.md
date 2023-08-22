# Contact List Application
    A simple application to manage contacts, built with Spring Boot for the API and Angular for the frontend.

## Prerequisites
- Docker (for Docker deployment)
- Java (for manual API deployment)
- Node.js and npm (for manual UI deployment)

### Deployment
#### Using Docker
    If you have Docker installed, this is the easiest way to run the application:

- Navigate to the project directory (/etc/local):
    cd /etc/local
- Build and start the Docker containers:
  docker-compose up --build

### Manual Deployment
#### API
- Navigate to the project's root folder:
    cd path_to_project_root

- Run the API using Gradle:
  ./gradlew bootRun

#### UI
- Navigate to the web directory inside the project:
    cd path_to_project_root/web

- Install the necessary npm packages:
  npm install

- Start the Angular application:
  npm start

## Architecture
### API 
    Framework: Spring Boot 3.
    Data Access: Spring Data JPA.
    Database: H2 in-memory database. This makes it easier to switch out to another database service in the future.
  
### UI
    Framework: Angular.
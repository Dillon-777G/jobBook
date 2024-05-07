# jobBook Application

| Project Members  | Area  |
|------------------|-------|
| Dillon Gaughan   | User  |
| Ravi Dhondkar    | Job   |
| Alexander Lazarov| Company |
| Malav Padhya     | Post  |


## **Purpose**:

We have decided to create a Job Search web application similar to linked in. Our goal is to
create a functional prototype that allows users to create a profile, search for jobs, and interact
with employers.

## **Conflict Resolution**:

We can resolve conflicts internally by discussing them as a group as needed in our chat or during the meeting. If need be we can contact the professor for guidance.

## **Communication Mechanism**:

We have chosen discord as our communication mechanism as it meets all of our needs. We will have one official meeting a week and stay in consistent.

## **Decision Made**:

| Category              | Choice       | Alternative  |  Decision Date |
|-----------------------|--------------|--------------|----------------|
| **IDE**               | VSCode       | IntelliJ     |  04/13/24      |
| **Dependency Management** | Maven    | Gradle       |  04/13/24      |
| **Code**              | Lombok for reducing boilerplate code; JUnit for unit testing | implement boilerplate code manually | 04/13/24  |
| **Configuration Management** |  application.properties | yml | 04/13/24    |
| **Doucmentation**     | OpenAPI, Swagger | Javadocs |   05/03/24     |
| **Database**          | h2           | mariadb      |   04/24/24     |


## **Lessons Learned**:

Currently our project uses the H2 database, which we have found much easier to use than our original plan of using mariadb. 
This change allowed us to speed up our development marginally, gaining insight into how using APIs with a lower learning curve can be beneficial under deadlines.


## How to Use This Project

### Prerequisites

- Java 21
- Maven (for dependency management)

### Setup and Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourgithubusername/jobsearchapp.git
2. Navigate to the project directory
   ```bash
   cd jobBook
3. Build the application   
   ```bash
   mvn package
4. Run the application
   ```bash
   mvn spring-boot:run

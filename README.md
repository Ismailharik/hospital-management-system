# Health Connect


<div align="center" >
 <img  width="35%" src="./images/logo.png">
</div>
<p>Health Connect is a full-stack application that aims to connect doctors and patients in a seamless and efficient manner. The application provides a user-friendly interface for managing medical appointments, accessing patient records, and facilitating communication between doctors and patients. Health Connect is built using Flutter for the frontend, Spring Boot 3 for the backend, MongoDB for the database, and utilizes Spring Security for enhanced security features. The application is deployed using Docker, ensuring easy and scalable deployment options. It also leverages Jib for containerized builds, Zipkin for distributed tracing, and OpenAPI for API documentation.
</p>


## Features
* <b>Patient:</b> 
  * Registration and Authentication: Allow patients to create accounts and securely authenticate.
  * Put their feed back on any doctor.
  * Make an appointement and choose the date they want.
     
* <b>Doctors:</b> 
  * Doctors accounts are created by the Admin, not everyone can be registred as a doctor.
  * Doctors can consult all appointements and confirm them.
  * 
* <b>Admin:</b> 
  * Have all rights on the application.

## Technologies Used
* Flutter: A cross-platform framework for building mobile, web, and desktop applications using Dart programming language.
* Spring Boot 3: A Java-based framework for building scalable and production-grade backend applications.
* MongoDB: A NoSQL database for storing and retrieving doctor, patient, and appointment information.
Spring Security: Handles user authentication, authorization, and overall application security.
* Docker: Enables containerized deployment and scalability of the application.
* Jib: A container image builder for Java applications, simplifying the creation of Docker images without Dockerfile.
* Zipkin: A distributed tracing system that helps gather timing data for requests across various microservices.
* OpenAPI: A specification and tooling for documenting and visualizing RESTful APIs.

## Getting Started
<p>To get started with Health Connect, follow these steps:</p>


1. Clone the repository: `git clone <repository-url> `
2. Set up the backend: 
* Configure MongoDB connection settings in the backend's `application.properties` file.
* Build and run the Spring Boot application using your preferred IDE or by running `mvn spring-boot:run` command.
3. Set up the frontend:
* Configure the backend API URL in the Flutter frontend's `lib/config.dart` file.
* Run the Flutter application using your preferred IDE or by running `flutter run` command.


<p>Now, you should be able to access Health Connect through the Flutter frontend.</p>


## Deployment
<p>Health Connect can be easily deployed using Docker. Follow these steps to deploy the application using Docker:</p>

1. Build a Docker image using Jib: Run `mvn jib:dockerBuild` in the root directory of the backend project or checkout intellij plugins.

2. To run all the backend you will need only to run ` docker-compose up`.


## Contributing
Contributions to the Health Connect project are welcome! If you would like to contribute, please follow these steps:

1. Fork the repository.
2. Create your feature branch: git checkout -b feature/new-feature.
3. Commit your changes: git commit -am 'Add new feature'.
4. Push the branch: git push origin feature/new-feature.
5. Submit a pull request.

Please ensure that your code adheres to the project's code style and conventions. Additionally, include tests for your changes to maintain code quality.


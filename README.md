# Project Title
WIP Playtomic like application using a micro services architecture

### Prerequisites
```
Java 17
```

## Project Overview
This project utilizes Eureka for service discovery and is structured as a multi-module application comprising several microservices.
The architecture facilitates parallel execution of these services and includes a gateway that handles security and forwards requests to the appropriate microservices.

## Getting Started
To run the application, follow these steps:

* Set Up the User Service:
  * Specify the profile in the user-service and generate the RSA keys.

* Launch the Eureka Server:
  * Start the Eureka server first to allow other services to register.

* Start Other Microservices
  * After the Eureka server is running, launch the other microservices so they can be discovered.

* Make a Request to the Gateway
  * By default, the gateway runs on port 8080. You can make requests to this endpoint to interact with the services.

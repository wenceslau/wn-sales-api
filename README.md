# wn-sales-api
Project Payment Provider, Wenceslau Neto Sales API

______________________________________________________________
# Recommended use docker compose to run and test de applications
## Instructions below
_______________________________________________________________

# INFORMATION

Java JDK 11

Spring 2.7.4

Swagger open API V3

Data base H2 memory

IDE IntelliJ

Maven

# COMMAND TO COMPILE 
#### mvn clean install
#### Run inside the root path

# COMMAND TO RUN
#### java -jar .\star-wars-network-0.0.1-SNAPSHOT.jar

# Swagger access URL, API documentation
#### http://localhost:8083/swagger-ui.html

# H2 Database memory access
#### http://localhost:8083/h2-console/login.jsp
#### URL.: jdbc:h2:mem:wn-sales-api
#### USER: sa
#### PASS: password

# DOCKER COMPOSE

Project has 3 docker files.
- dockerfile-api      - Create image of Java API
- dockerfile-ui       - Create image of Angula UI
- docker-compose.yaml - Create containers and network for them

## Run the command below to start containers
docker compose up -d
### If the logs below showing, indicate the applications is up 

[+] Running 3/3
- Network wn-sales-api_salesnet  Created    0.8s
- Container sales-api            Started    1.6s
- Container sales-ui             Started    0.5s
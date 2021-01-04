# Getting Started

A RESTful service exposing endpoints for Orders, Baskets

Stores the orders, baskets in a SQL database hosted in Google Cloud.

Clone the project from repository using GIT
cd to the project directory

### Configure Service Account Access
1. Download key file for the service account which has roles to access the CloudSQL
2. Export environment variable as below
        export GOOGLE_APPLICATION_CREDENTIALS=/path/to/key/file.json

### RUN cloud sql proxy

- [Download SQL Proxy]
        1. Instructions to download (https://cloud.google.com/sql/docs/mysql/quickstart-proxy-test)
        2. For Mac
         curl -o cloud_sql_proxy https://dl.google.com/cloudsql/cloud_sql_proxy.darwin.amd64
        

1.  Make the proxy executable:
    chmod +x cloud_sql_proxy
2.  Run the Cloud SQL Proxy for database connections
    ./cloud_sql_proxy -instances=nodal-formula-295821:europe-west2:sql-instance=tcp:3306

### Connect MYSQL from mysql command prompt
1. Install MySQL Client on Mac
        brew install mysql
   
1.  Connect to your database using the mysql client
    mysql -u <USERNAME> -p --host 127.0.0.1 --port 3306
    mysql -u root -p --host 127.0.0.1 

### Run the micro service and Connect MYSQL 
1. Perform the above steps
2. cd [project-dir for order-service]
3. Build the project
        gradle clean build
4. Run the spring boot service
        SPRING_PROFILES_ACTIVE=proxy gradle bootRun

### How to connect to MYSQL with cloud sql proxy as docker image

1. Run the following docker command
        docker run  \
        -v ${GOOGLE_APPLICATION_CREDENTIALS}:/config \
        -p 127.0.0.1:3306:3306 \
        gcr.io/cloudsql-docker/gce-proxy:1.16 /cloud_sql_proxy \
        -instances=nodal-formula-295821:europe-west2:sql-instance=tcp:127.0.0.1:3306 -credential_file=/config
2. To Connect to your database using the mysql client
        mysql -u <USERNAME> -p --host 127.0.0.1 --port 3306
3. To connect from microservice
        SPRING_PROFILES_ACTIVE=proxy gradle bootRun

### How to connect to MYSQL with cloud sql proxy as docker image from docker-compose

1. Build the project
        gradle clean build
2. Copy the key.json file to ~/.config/gcloud
3. Rename the file to application_default_credentials.json
4. Build the docker images from docker-compose file
        docker-compose build
3. RUN containers
        docker-compose up

### Reference Documentation

For further reference, please consider the following sections:

- [Official Gradle documentation](https://docs.gradle.org)
- [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.0/gradle-plugin/reference/html/)
- [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.0/gradle-plugin/reference/html/#build-image)
- [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#using-boot-devtools)
- [Spring Web](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#boot-features-developing-web-applications)
- [Spring Security](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#boot-features-security)
- [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#boot-features-jpa-and-spring-data)
- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#production-ready)

### Guides

The following guides illustrate how to use some features concretely:

- [Building the microservice Locally]()

- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
- [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
- [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
- [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Additional Links

These additional references should also help you:

- [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

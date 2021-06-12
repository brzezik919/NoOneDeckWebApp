# NoOneDeckWebApp
Web version of my simple yugioh app

This is a simple application used to manage users cards beetween themselves and their teams. Application is using Java, Keycloak, MariaDB. Tutorial is made on Ubuntu operating system.

This project is based on Spring, Hibernate and JPA. https://spring.io/projects/spring-boot. User interface is generated by templates from Thymeleaf. 

# Install and configure Keycloak

Download the latest version of Keycloak (I used 13.0.1): https://www.keycloak.org/downloads.

Extract the archive into folder and use following commands in terminal:
- `cd keycloak-13.0.1/bin/`
- `sudo ./standalone.sh -Djboss.socket.binding.port-offset=100`

Parameter port-offset is standard keycloak ports + paramether. Keycloak application is using port 8080, but Spring app can't be compiled while using the same port and that's why it needs to be changed.

Keycloak configuration realms : https://www.keycloak.org/docs/latest/server_admin/

My KeyCloak configuration file: *****.

# Import Data to DataBase

You need to create database called "nooneapp"

After you run application and all entities are created, you can use the following SQL file to import all CardNames in YuGiOh: ******.
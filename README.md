# OnlineStoreProject
First Project

This project is myt very first Java project, initially wanted to do several mini (even smaller) projects but realized that they would likely be copies
of x portion of this one. This was my first attempt at becoming more familiar with the Spring Framework/Spring Boot in conjunction with Maven builds, Hibernate/JPA and the MVC architecture in 
a more hands on fashion. 


Below are some of the configuration settings in the project.


                                    

    	                             File Structure



OnlineStore

    OnlineStoreProject

    	OnlineStoreCommon
    		  Contains the common entity classes, along with exception classes
    		  Dependencies are added to FrontEnd/BackEnd


    	OnlineStoreWebParent
    		  OnlineStoreFrontEnd (child of OnlineStoreWebParent, dependency added)
    		  OnlineStoreBackEnd  (child of OnlineStoreWebParent, dependency added)
    		  contains several xml configs that apply across both the front and backend


POM XML files are included in project.

                  
                          
                          
                          
                          OnlineStoreBackEnd : Data Source Configurations

Database used in the project can be of any type assuming appropriate configurations are made, Posgresql was chosen for this
project. While Spring/Hibernate/JPA does create tables, it is important to ensure that the restrictions we want to put in
are actually being enforeced, make sure to enforce on the Database side.

application properties:

server.port=8080
server.servlet.context-path=/OnlineStoreAdmin
spring.datasource.platform=postgres
spring.datasource.url=jdbc:postgresql://localhost:5432/onlinestore (or whatever port and database name you prefer to use for data connection)
spring.datasource.username= (enter your username)
spring.datasource.password= (enter your password
spring.datasource.driverClassName=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true



                          OnlineStoreFrontEnd  : Data Source Configurations   



application properties:

server.port=80
server.servlet.context-path=/HomeSite

spring.datasource.platform=postgres
spring.datasource.url=jdbc:postgresql://localhost:5432/onlinestore (or whatever port you prefer to use for data connection)
spring.datasource.username= (enter your username)
spring.datasource.password= (enter your password
spring.datasource.driverClassName=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Instructions

* Make sure that you have mysql installed and able to run, login with proper credentials. 
```
mysql -u root -p
```
* Execute the .sql script, you can find it in this project.
```
source 'scripts.sql' (or any path)
```
* Make sure that your maven is bundled with Java 8.
* cd into the project, clean and package it and then run it.
```
mvn clean
```
```
mvn package
```
```
mvn spring-boot:run
```
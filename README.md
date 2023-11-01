# UserPost
![Java 100%](https://img.shields.io/badge/Java-100%25-%23E57300)
![Maven](https://img.shields.io/badge/Maven-%238a6ac8?style=for-the-badge&logo=apache-maven&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-blue?style=for-the-badge&logo=mysql&logoColor=white)
### Description
This is a simple blog application that allows the user to create a user and create blog posts. The user can also use basic crud which means
that they are able to update and delete their posts as well. The main focus with this project was to learn encrypt passwords into the database by using
bcrypt and salting. 

## Table of Contents

+ [Installation](#installation)
+ [Usage](#usage)
+ [Dependencies](#Dependencies)

## Installation
*Make sure you these downloaded before you start*
+ An IDE of your choice preferably intelliJ [Here](https://www.jetbrains.com/idea/download/#section=windows) and the latest [JDK 21](https://www.oracle.com/se/java/technologies/downloads/)
+  [MySQL Community Server](https://dev.mysql.com/downloads/mysql/) - Download community server and make sure you download the latest server and workbench
+ Clone this GitHub repository to your computer or download it.
+ 
## Usage
*before you start the program*
+ start the mysql workbench and create a new database called userpost like this
```
CREATE DATABASE userpost;
```
+ create a .env file in the resources folder, so you can add your password, follow the [.env.example](src/main/resources/.env.example) and add your mysql password in your .env file

+ now go to the main class and start the program

## Dependencies
+ [junit jupiter 5](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api/5.10.0)
+ [MySQL Connector Java](https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.33)
+ [JBCrypt ](https://mvnrepository.com/artifact/org.mindrot/jbcrypt/0.4)
+ [Mockito Core](https://mvnrepository.com/artifact/org.mockito/mockito-core/5.6.0)
+ [Java Dotenv](https://mvnrepository.com/artifact/io.github.cdimascio/java-dotenv/5.2.2)

## License

The last section of a high-quality README file is the license. This lets other developers know what they can and cannot do with your project. If you need help choosing a license, refer to [MIT License](https://choosealicense.com/licenses/mit/).
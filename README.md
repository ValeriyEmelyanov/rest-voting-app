# REST Voting System for deciding where to have lunch

This application uses:
* Spring Boot, Spring MVC
* Maven to build Spring Project
* Data-JPA, HSQLDB (in memory database)
* Tests with JUnit 5 & Mockito

### Task
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant and it's lunch menu_id of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote on which restaurant they want to have lunch at
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we asume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides new menu_id each day.

As a result, provide a link to github repository.

It should contain the code and README.md with API documentation and curl commands to get data for voting and vote.

P.S.: Make sure everything works with latest version that is on github :)

P.P.S.: Asume that your API will be used by a frontend developer to build frontend on top of that.

## Solution

#### Concise description
* To use the REST service API user have to be authenticated with email and password
* 2 types of users: admin and regular users
* Admin has full access to users, restaurants, menus and votes
* Regular user can read restaurants, menus and has full access to vores
* Regular users can vote on which restaurant they want to have lunch at once a day
* If user votes again the same day: 
  * If it is before 11:00 we asume that he changed his mind
  * If it is after 11:00 then it is too late, vote can't be changed
  
#### API Documentation

API documentation is [here](Doc/api.md).
  
[Request Examples for Postman](Doc/rest-voting-app.postman_collection.json)


### Usefull references
* Databases
  * <a href="http://deskbook.info/hibernate/annotations/2-annotation-jpa.html">Аннотации JPA</a>
  * <a href="http://deskbook.info/hibernate/inheritance/11-jpa-mappedsuperclass.html">Аннотация @MappedSuperclass</a>
  * <a href="https://ru.stackoverflow.com/questions/874276/%D0%94%D0%BB%D1%8F-%D1%87%D0%B5%D0%B3%D0%BE-accesstype-field-%D0%B8-accesstype-property-%D0%B2-access">Для чего AccessType.FIELD и AccessType.PROPERTY в @Access?</a>
  * <a href="https://javatalks.ru/topics/14436">Можно небольшой вводный пример по HSQLDB?</a>  
* Security
  * <a href="https://stackoverflow.com/questions/19525380/difference-between-role-and-grantedauthority-in-spring-security">Difference between Role and GrantedAuthority in Spring Security</a>
  * <a href="https://stackoverflow.com/questions/30819337/multiple-antmatchers-in-spring-security">Multiple antMatchers in Spring security</a>
* Assurance
  * <a href="http://appsdeveloperblog.com/tag/rest-assured/">REST-assured</a>
* App / Project Examples
  * <a href="https://o7planning.org/ru/11705/create-a-login-application-with-spring-boot-spring-security-jpa">Создайте приложение для входа с Spring Boot, Spring Security, JPA</a>
  * <a href="https://www.mkyong.com/spring-boot/spring-rest-spring-security-example/">Spring REST + Spring Security Example</a>
* HTTP codes
  * <a href="https://tools.ietf.org/html/rfc7231#section-6.5.8">6.5.8.  409 Conflict</a>
* cUrl
  * <a href="https://www.mkyong.com/spring/curl-post-request-examples/">cURL – POST request examples</a>
* API Documentation
  * <a href="https://gist.github.com/iros/3426278">Documenting your REST API</a>

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

... in process

### Usefull references
* Databases
  * <a href="http://deskbook.info/hibernate/annotations/2-annotation-jpa.html">Аннотации JPA</a>
* Security
  * <a href="https://stackoverflow.com/questions/19525380/difference-between-role-and-grantedauthority-in-spring-security">Difference between Role and GrantedAuthority in Spring Security</a>
  * <a href="https://o7planning.org/ru/11705/create-a-login-application-with-spring-boot-spring-security-jpa">Создайте приложение для входа с Spring Boot, Spring Security, JPA</a>
  * <a href="https://stackoverflow.com/questions/30819337/multiple-antmatchers-in-spring-security">Multiple antMatchers in Spring security</a>
* Assurance
  * <a href="http://appsdeveloperblog.com/tag/rest-assured/">REST-assured</a>
  * <a href=""></a>

# Swapi-integration-API
######`May the Force be with you.....`

### Database credentials 
If you want to check database records and relations, please run the application and provide following details to your H2 Database console: 
* [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
* Driver Class:  <b> org.h2.Driver </b>
* JDBC URL: <b>jdbc:h2:mem:starwars </b>
* User Name: <b>anakin</b>
* Password:	<b>skywalker</b>


#### Stack
* Java 11 (v. 11.0.3)
* Spring : Boot, Cloud, Web, Data, Tests
* Database : H2 memory


####Build
* Fat war with all dependencies: <b>jedi-api.war</b> 

####Application settings
* Standart port: <b>8080</b>
* application.name <b> swapi-integration-api </b>
* jmx.default-domain <b> swapi-integration-api </b>



###Maybe in feature / TODO
* Caffeine cache with async - CompletableFeature
* Feign ErrorDecoder
* Hystrix FallbackFactory / Dashboard
* Feign Circuit Breaker
* Actuator for request history
* @FunctionalInterface
* Tests

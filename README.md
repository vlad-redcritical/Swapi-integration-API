# Swapi-integration-API
###`May the Force be with you.....`


### Stack
* Java 11 (v. 11.0.3)
* Spring : Boot, Cloud, Web, Data, Tests
* Database : H2 memory
* Caffeine Cache
* AOP

### Build
* Fat war with all dependencies: <b>jedi-api.war</b> 

### Application settings
#### Spring boot configuration
```
spring.application.name=swapi-integration-api
spring.jmx.default-domain=swapi-integration-api
```
#### Spring cloud configuration
```
logging.level.pl.softwareplant.api.client.SwapiClient:DEBUG
```

#### H2 Database configuration
```
spring.datasource.url=jdbc:h2:mem:starwars
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=anakin
spring.datasource.password=skywalker
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

#### Client search settings
```
search.client.default.startPage=1
search.client.default.perPage=10
```

### Database credentials 
If you want to check database records and relations, please run the application and provide following details to your H2 Database console: 
* [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
* Driver Class:  <b> org.h2.Driver </b>
* JDBC URL: <b>jdbc:h2:mem:starwars </b>
* User Name: <b>anakin</b>
* Password:	<b>skywalker</b>


### Caffeine Cache 
Caffeine cache provided for repetable call execution: planets and films. 
* Default cache time expiration  - <b>4 minutes</b> @see {expireAfterWrite(duration, TimeUnit)}
```Java
    @Bean
    Cache<String, String> planetHomeWordCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(4, TimeUnit.MINUTES)
                .build();
    }

    @Bean
    Cache<String, String> filmsDetailsCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(4, TimeUnit.MINUTES)
                .build();
    }
```

### Aspect Annotations
Added Aspect annotation: @ExecutionTimeLogger (based on JoinPoint) - give to the console information regarding measure elapsed time of function execution: 
```Java
    @ExecutionTimeLogger
    @Override
    public List<CharacterDetailsResults> findPeopleByCriteria(QueryCriteriaDto queryCriteriaDto) {
      ...
    }
```

Console output: 
```
TimeLogger      : Report generation info: className=com.yourpath.api.client.service.impl.IntegrationServiceImpl, methodName=findPeopleByCriteria, timeMs=9884, threadId=128

```


### Maybe in feature / TODO
* Feign ErrorDecoder
* Hystrix FallbackFactory / Dashboard
* Feign Circuit Breaker
* Actuator for request history
* @FunctionalInterface
* Tests

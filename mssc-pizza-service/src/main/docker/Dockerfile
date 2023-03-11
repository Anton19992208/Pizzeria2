FROM eclipse-temurin:17

WORKDIR /app

COPY target/mssc-pizza-service-0.0.1-SNAPSHOT.jar /app/pizza-service.jar

ENTRYPOINT ["java", "-jar", "pizza-service.jar"]
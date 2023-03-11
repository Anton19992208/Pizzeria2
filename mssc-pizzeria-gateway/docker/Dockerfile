FROM eclipse-temurin:17

WORKDIR /app

COPY target/mssc-pizzeria-gateway-0.0.1-SNAPSHOT.jar /app/pizza-gateway.jar

ENTRYPOINT ["java", "-jar", "pizza-gateway.jar"]
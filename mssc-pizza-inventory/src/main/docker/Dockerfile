FROM eclipse-temurin:17

WORKDIR /app

COPY target/mssc-pizza-inventory-0.0.1-SNAPSHOT.jar /app/pizza-inventory.jar

ENTRYPOINT ["java", "-jar", "pizza-inventory.jar"]
FROM eclipse-temurin:17

WORKDIR /app

COPY target/mssc-pizza-inventory-failover-0.0.1-SNAPSHOT.jar /app/pizza-inventory-failover.jar

ENTRYPOINT ["java", "-jar", "pizza-inventory-failover.jar"]
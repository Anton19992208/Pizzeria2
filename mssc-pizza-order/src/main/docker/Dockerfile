FROM eclipse-temurin:17

WORKDIR /app

COPY target/mssc-pizza-order-0.0.1-SNAPSHOT.jar /app/pizza-order.jar

ENTRYPOINT ["java", "-jar", "pizza-order.jar"]
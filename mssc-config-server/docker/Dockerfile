FROM eclipse-temurin:17

WORKDIR /app

COPY target/mssc-config-server-0.0.1-SNAPSHOT.jar /app/config-server.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "config-server.jar"]
FROM eclipse-temurin:22-jre
WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENV SPRING_PROFILES_ACTIVE=postgres
ENV TZ=UTC

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
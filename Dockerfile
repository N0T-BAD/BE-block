FROM openjdk:17-jdk-slim
COPY build/libs/block-service-0.0.1-SNAPSHOT.jar block-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/block-service.jar"]

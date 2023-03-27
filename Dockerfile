FROM openjdk:latest
LABEL maintainer="stefan.net"
ADD target/SpringKafkaTaskProducer-0.0.1-SNAPSHOT.jar spring-producer.jar
COPY src/main/resources/application.properties /app/
ENTRYPOINT ["java", "-jar", "spring-producer.jar"]
FROM openjdk:11-jdk-slim
COPY target/assessment-0.0.1-SNAPSHOT.jar assessment-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/assessment-0.0.1-SNAPSHOT.jar"]
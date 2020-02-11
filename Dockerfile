FROM openjdk:8-jdk-alpine

ADD *.jar /opt/ms/app.jar

EXPOSE 8080
CMD ["java", "-jar", "/opt/ms/app.jar"]
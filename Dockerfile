FROM openjdk:11-jre
COPY target/dinner-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

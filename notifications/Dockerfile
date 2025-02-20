FROM openjdk:17
EXPOSE 8080
ADD ./build/libs/*.jar notifications.jar
ENTRYPOINT ["java", "-jar", "/notifications.jar"]
FROM openjdk:17
EXPOSE 8080
ADD ./build/libs/*.jar limits.jar
ENTRYPOINT ["java", "-jar", "/limits.jar"]
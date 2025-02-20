FROM openjdk:17
EXPOSE 8080
ADD ./build/libs/*.jar core-transfers.jar
ENTRYPOINT ["java", "-jar", "/core-transfers.jar"]
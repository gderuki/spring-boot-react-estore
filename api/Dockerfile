FROM amazoncorretto:22-alpine
COPY target/*-SNAPSHOT.jar app.jar
EXPOSE 5005
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "app.jar"]
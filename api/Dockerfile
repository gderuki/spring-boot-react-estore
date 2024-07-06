FROM amazoncorretto:22-alpine

COPY target/*-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
FROM openjdk:14-alpine
COPY target/spring-with-mn-data-*.jar spring-with-mn-data.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "spring-with-mn-data.jar"]
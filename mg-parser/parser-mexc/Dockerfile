FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar mg_parser_mexc.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/mg_parser_mexc.jar"]

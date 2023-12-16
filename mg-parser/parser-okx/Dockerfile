FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar mg_parser_okx.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/mg_parser_okx.jar"]

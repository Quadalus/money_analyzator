FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar mg_parser_huobi.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/mg_parser_huobi.jar"]

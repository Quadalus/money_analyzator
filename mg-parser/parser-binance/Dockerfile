FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar mg_parser_binance.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/mg_parser_binance.jar"]

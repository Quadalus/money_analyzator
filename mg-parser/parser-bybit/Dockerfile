FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar mg_parser_bybit.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/mg_parser_bybit.jar"]

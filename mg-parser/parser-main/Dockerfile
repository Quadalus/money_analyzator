FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar mg_parser_main.jar
EXPOSE 8080
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8060
ENTRYPOINT ["java", "-jar", "/mg_parser_main.jar"]

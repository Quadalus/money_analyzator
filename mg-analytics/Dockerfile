FROM amazoncorretto:17-alpine
COPY target/*.jar mg_analyzer.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "/mg_analyzer.jar"]

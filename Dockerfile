FROM amazoncorretto:17
RUN dnf install -y tzdata && dnf clean all
RUN groupadd --system spring && useradd --system --gid spring spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]

FROM nexus-docker-public-hosted.ossim.io/openjdk11:alpine-slim

EXPOSE 8080

WORKDIR /app
COPY *.jar application.jar

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/application.jar"]
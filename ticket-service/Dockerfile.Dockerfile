FROM adoptopenjdk:11-jre-hotspot
LABEL maintainer="zahra_riahi"
#only for local
ENV JAVA_HOME C:\Program Files\Java\jdk1.8.0_333
COPY /target/ticket-service-0.0.1-SNAPSHOT.jar /app/
WORKDIR /app
ENTRYPOINT ["java","-jar","ticket-service-0.0.1-SNAPSHOT.jar"]


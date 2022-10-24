#
# Build stage
#
FROM maven:3.8.6-jdk-8-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
CMD "pwd"
RUN mvn -ntp -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:8-jre-slim
COPY --from=build /home/app/target/store-0.0.1-SNAPSHOT.jar /usr/local/lib/store.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","/usr/local/lib/store.jar"]
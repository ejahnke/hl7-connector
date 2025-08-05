FROM --platform=linux/amd64 maven:3-amazoncorretto-19 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean compile assembly:single

FROM --platform=linux/amd64 amazoncorretto:19
COPY --from=build /home/app/target/hl7-connector-0.0.1-SNAPSHOT-jar-with-dependencies.jar /usr/local/hl7receiver/hl7connector.jar
COPY --from=build /home/app/target/classes /usr/local/hl7receiver/classes
RUN yum -y install procps
EXPOSE 18000
ENTRYPOINT ["java","-jar","/usr/local/hl7receiver/hl7connector.jar"]

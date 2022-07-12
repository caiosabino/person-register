FROM gradle:6-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM adoptopenjdk/openjdk11

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/person-register-0.0.1-SNAPSHOT.jar /app/person-register-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/app/person-register-SNAPSHOT.jar"]
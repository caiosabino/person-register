FROM gradle:6-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM adoptopenjdk/openjdk11

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/person.jar /app/person-register-1.0-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/app/Authorizing-1.0-SNAPSHOT.jar"]
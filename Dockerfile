FROM amazoncorretto:21 AS build
WORKDIR /home/gradle/src

COPY gradlew gradlew
COPY gradle gradle
COPY settings.gradle build.gradle ./
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar --no-daemon

FROM amazoncorretto:21
WORKDIR /app

EXPOSE 8080

COPY --from=build /home/gradle/src/build/libs/person-register-0.0.1-SNAPSHOT.jar /app/person-register.jar

ENTRYPOINT ["java","-jar","/app/person-register.jar"]

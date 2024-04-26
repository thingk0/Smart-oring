FROM gradle:jdk21 as builder

WORKDIR /app
COPY . ./
RUN gradle clean build

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*SNAPSHOT.jar /app/app.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","app.jar"]

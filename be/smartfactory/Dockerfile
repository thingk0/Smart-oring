FROM gradle:jdk21 as builder

WORKDIR /app
COPY . ./
RUN gradle clean build

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/factory-0.0.1.jar /app/app.jar
EXPOSE 10909
ENTRYPOINT ["java","-jar","app.jar"]

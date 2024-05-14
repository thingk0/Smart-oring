# 빌드 스테이지
FROM gradle:8.7.0-jdk17 as builder

WORKDIR /app

# 소스 코드를 복사
COPY . .

# gradlew 파일에 실행 권한 부여
RUN chmod +x ./gradlew

# 프로젝트 빌드
RUN ./gradlew clean build -x test --no-daemon

# 실행 스테이지
FROM openjdk:17-jdk-slim
WORKDIR /app

# 환경 변수 설정
ENV JAVA_OPTS="-Xms1024m -Xmx1024m"

# 빌드 결과물을 복사
COPY --from=builder /app/build/libs/amr-streams-0.0.1.jar /app/app.jar

# 포트 노출
EXPOSE 10910

# 애플리케이션 실행
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]

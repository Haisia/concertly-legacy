# 1. 빌드 이미지 (Gradle을 사용하여 Jar 파일을 빌드)
FROM gradle:8.1.1-jdk17 AS build
WORKDIR /app

# 프로젝트의 모든 내용을 복사 (Gradle 빌드에 필요한 파일 포함)
COPY . .

# gradlew 실행 권한 추가
RUN chmod +x ./gradlew

# Gradle clean 및 bootJar 실행하여 JAR 생성
RUN ./gradlew clean bootJar

# 2. 실행 이미지 (최종 JAR 파일만 포함)
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# 빌드 단계에서 생성된 JAR 파일 복사
COPY --from=build /app/build/libs/concertly-legacy-0.0.1-SNAPSHOT.jar ./app.jar

# 애플리케이션 실행
CMD ["sh", "-c", "java -Dspring.profiles.active=prod -jar app.jar"]
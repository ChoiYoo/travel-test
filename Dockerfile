# server dockerfile
# Step 1: Build the application
FROM gradle:7.5.1-jdk17 AS builder

WORKDIR /app

# Gradle Wrapper 및 설정 파일 복사 (의존성 캐싱)
COPY gradlew ./
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# 의존성 설치 및 캐시 활용
RUN ./gradlew dependencies --no-daemon

# 소스 코드 복사 및 애플리케이션 빌드
COPY . .
RUN ./gradlew build -x test --no-daemon -Pprofile=prod

# Step 2: Create the actual image to run the application
FROM amazoncorretto:17-alpine
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

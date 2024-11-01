# Step 1: Create the actual image to run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# 로컬에서 빌드된 JAR 파일 복사
COPY build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

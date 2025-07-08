# 使用 OpenJDK 21 作為基礎映像
FROM eclipse-temurin:21-jdk-jammy

# 設定容器內的工作目錄
WORKDIR /app

# 複製本地已經 build 好的 JAR 檔案
COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar

# 開放 Spring Boot 預設的 8080 port
EXPOSE 8080

# 啟動 Spring Boot 應用
ENTRYPOINT ["java", "-jar", "app.jar"]

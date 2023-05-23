# Docker taban imajı
FROM openjdk:11-jdk-slim

# Uygulama paketinin Docker imajına kopyalanması
COPY target/app.jar app.jar

# Uygulamanın çalıştırılması
CMD ["java", "-jar", "app.jar"]
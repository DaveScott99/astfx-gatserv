FROM eclipse-temurin:17-jre-alpine
LABEL authors="Davi Santos"
WORKDIR /app
COPY target/gateway-1.1.5.jar gateway-1.1.5.jar
EXPOSE 8763
CMD ["java", "-jar", "gateway-1.1.5.jar"]
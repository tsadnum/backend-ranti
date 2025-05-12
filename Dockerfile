# Imagen base con JDK 17
FROM openjdk:17-jdk-slim

# Zona horaria
ENV TZ=America/Lima

# Directorio de trabajo
WORKDIR /app

# Copiar el JAR (asegúrate de que solo haya un .jar o cámbialo por nombre exacto)
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Exponer puerto
EXPOSE 8080

# Comando por defecto
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "app.jar"]

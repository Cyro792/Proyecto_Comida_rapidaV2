# 1. Usar una imagen oficial de Java 17 como base
FROM eclipse-temurin:17-jdk-alpine

# 2. Establecer la carpeta de trabajo dentro del contenedor
WORKDIR /app

# 3. Copiar tu archivo empaquetado (.jar) generado por Maven hacia el contenedor
COPY target/*.jar app.jar

# 4. El puerto por el que tu Catálogo se comunica con el mundo
EXPOSE 8082

# 5. El comando que ejecutará Docker para encender la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
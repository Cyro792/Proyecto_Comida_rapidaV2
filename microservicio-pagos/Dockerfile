# ----- ETAPA 1: Compilación del proyecto -----
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copiamos el pom.xml y las dependencias para guardarlas en caché
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código fuente de tu microservicio (la carpeta src)
COPY src ./src

# Compilamos y saltamos los tests para que no pierda tiempo (ya sabemos que dan verde)
RUN mvn clean package -DskipTests

# ----- ETAPA 2: Ejecución de la aplicación -----
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Tomamos el .jar generado en la etapa anterior y lo movemos a esta imagen limpia
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto de Pagos (8082)
EXPOSE 8082

# Arrancamos la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
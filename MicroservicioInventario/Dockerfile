# 1. Usamos una imagen oficial de Java 17 para correr la app
FROM eclipse-temurin:17-jdk-alpine

# 2. Creamos una carpeta de trabajo dentro del contenedor
WORKDIR /app

# 3. Copiamos el archivo compilado .jar de nuestra app al contenedor
COPY target/MicroservicioInventario-0.0.1-SNAPSHOT.jar app.jar

# 4. Exponemos el puerto 8081 que usa tu microservicio de inventario
EXPOSE 8081

# 5. Comando para ejecutar la aplicación apenas encienda el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]
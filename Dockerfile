# 1️⃣ Usamos la imagen oficial de Gradle con Java 21
FROM gradle:8.5-jdk21 AS build

# 2️⃣ Establecemos el directorio de trabajo
WORKDIR /app

# 3️⃣ Copiamos el código fuente
COPY . .

# 4️⃣ Construimos el proyecto (genera el JAR)
RUN gradle build --no-daemon

# 5️⃣ Usamos una imagen más liviana para ejecutar el JAR
FROM openjdk:21-jdk-slim

WORKDIR /app

# 6️⃣ Copiamos el JAR generado en la fase de compilación
COPY --from=build /app/build/libs/*.jar app.jar

# 7️⃣ Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]


# 1️⃣ Usamos una imagen oficial de OpenJDK 21 como base
FROM openjdk:21 AS build

# 2️⃣ Definimos el directorio de trabajo dentro del contenedor
WORKDIR /app

# 3️⃣ Copiamos los archivos del proyecto al contenedor
COPY . .

# 4️⃣ Instalamos Gradle dentro del contenedor (opcional si ya tienes Gradle Wrapper)
RUN apt-get update && apt-get install -y gradle

# 5️⃣ Construimos el proyecto usando Gradle (genera el archivo JAR)
RUN ./gradlew build --no-daemon

# 6️⃣ Extraemos la imagen mínima necesaria para ejecutar la aplicación
FROM openjdk:21

# 7️⃣ Definimos el directorio de trabajo donde correrá la app
WORKDIR /app

# 8️⃣ Copiamos el archivo JAR generado en la etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

# 9️⃣ Exponemos el puerto 8080 para que el contenedor acepte conexiones
EXPOSE 8080

# 🔟 Definimos el comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]

# Etapa de construcción: Usa una imagen de Gradle para construir la aplicación
FROM openjdk:17-jdk AS build

# Define argument for JAR file location
ARG JAR_FILE

# Crea un directorio en el contenedor para la aplicación
RUN mkdir /app

# Copy the built JAR into the container
COPY ${JAR_FILE} /app/spring-boot-application.jar

# Define el directorio de trabajo
WORKDIR /app

# Expone el puerto en el que se ejecutará la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "spring-boot-application.jar"]

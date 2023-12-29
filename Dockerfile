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

ENV SPRING_PROFILES_ACTIVE=prod
ENV APP_PORT=8082
ENV DATABASE_URL=jdbc:postgresql://postgres-user:5432/userdb
ENV DATABASE_USERNAME=myuser
ENV DATABASE_PASSWORD=seret

# Expone el puerto en el que se ejecutará la aplicación
EXPOSE ${APP_PORT}

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "spring-boot-application.jar"]

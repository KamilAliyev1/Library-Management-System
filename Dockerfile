FROM openjdk:17-jdk

EXPOSE 9000

WORKDIR /app

COPY gradlew .
COPY gradle gradle

COPY build.gradle .
COPY settings.gradle .

# Copy the source code
COPY src src

# Copy the built JAR to the container
COPY build/libs/FinalProject-0.0.1-SNAPSHOT-plain.jar FinalProject.jar

# Health check
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
  CMD curl --fail http://localhost:9000/ || exit 1

ENTRYPOINT ["java", "-jar", "FinalProject.jar"]
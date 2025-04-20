# Koristi JDK 17 kao baznu sliku
FROM eclipse-temurin:17-jdk

# Postavi radni direktorij
WORKDIR /app

# Kopiraj jar datoteku iz target foldera
COPY target/Wineries-0.0.1-SNAPSHOT.jar app.jar

# Expose porta koji koristi Spring Boot
EXPOSE 4040

# Komanda za pokretanje aplikacije
ENTRYPOINT ["java", "-jar", "app.jar"]
FROM jelastic/maven:3.9.5-openjdk-21 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /app
COPY --from=build /app/target/*.jar ./application.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "application.jar"]
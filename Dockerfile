# Docker 镜像构建
# @author <a href="https://github.com/liyupi">程序员鱼皮</a>
# @date 2024/10/14 19:00
FROM maven:3.8.1-jdk-8-slim as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package -DskipTests

# Run the web service on container startup.
CMD ["java","-jar","/app/target/brain-burned-game-backend-1.0.0-alpha.jar","--spring.profiles.active=prod"]
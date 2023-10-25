FROM openjdk:21-jdk-slim AS build
ENV APP_HOME=/usr/src
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
RUN chmod +x gradlew
RUN ./gradlew build || return 0
COPY . .
RUN chmod +x gradlew
RUN ./gradlew build

FROM openjdk:21-jdk-slim
ENV JAR_FILE=inventory-api-0.0.1.jar
COPY --from=build /usr/src/build/libs/$JAR_FILE /usr/src/
WORKDIR /usr/src
ENTRYPOINT java -jar $JAR_FILE
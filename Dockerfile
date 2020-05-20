FROM clojure:openjdk-11-lein-slim-buster AS build
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN lein do clean, ring uberjar

FROM openjdk:11-jre-slim
WORKDIR /usr/src/app
COPY --from=build /usr/src/app/target/server.jar ./server.jar
CMD ["java", "-jar", "server.jar"]
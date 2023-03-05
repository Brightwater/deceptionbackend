FROM adoptopenjdk/openjdk15:ubi
ENV APP_HOME=/usr/app/
WORKDIR ${APP_HOME}
COPY target/deception-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8076
CMD ["java","-XX:MaxRAM=128m","-jar","app.jar"]
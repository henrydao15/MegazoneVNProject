# For Java 11, try this
FROM openjdk:11.0.11-jre-slim-buster

WORKDIR /opt/app

COPY jars/ /opt/app

ENTRYPOINT ["sh", "docker-entrypoint.sh"]

EXPOSE 8443

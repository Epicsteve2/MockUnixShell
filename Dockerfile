FROM openjdk:11-jre-slim-bullseye

WORKDIR /app
COPY mock_unix_shell.jar /app/mock_unix_shell.jar 
CMD java -jar mock_unix_shell.jar

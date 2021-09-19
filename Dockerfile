FROM openjdk:11.0.12-jdk-slim-bullseye as install
RUN apt update && \
    apt install curl --yes && \
    sh -c "$(curl --location https://taskfile.dev/install.sh)" -- -d && \
    apt remove curl --yes

WORKDIR /app
COPY . /app

RUN task build-jar

FROM openjdk:11.0.12-jre-slim-bullseye

WORKDIR /app
COPY --from=install /app/mock_unix_shell.jar /app/mock_unix_shell.jar 
CMD java -classpath mock_unix_shell.jar driver.JShell

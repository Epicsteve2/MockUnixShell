FROM openjdk:11.0.12-jdk-slim-bullseye as install
RUN apt-get update && \
    apt-get install --yes --no-install-recommends curl && \
    sh -c "$(curl --location https://taskfile.dev/install.sh)" -- -d && \
    apt-get remove curl --yes

WORKDIR /app
COPY . /app

RUN task build-jar

FROM openjdk:11.0.12-jre-slim-bullseye

WORKDIR /app
COPY --from=install /app/mock_unix_shell.jar /app/mock_unix_shell.jar 
CMD java -jar mock_unix_shell.jar

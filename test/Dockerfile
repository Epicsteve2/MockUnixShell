FROM openjdk:11.0.12-jdk-slim-bullseye 
RUN apt update && \
    apt install curl --yes && \
    sh -c "$(curl --location https://taskfile.dev/install.sh)" -- -d && \
    apt remove curl --yes

RUN apt install junit4 --yes

WORKDIR /app
COPY ../. /app

CMD task test-run

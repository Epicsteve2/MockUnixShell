FROM openjdk:11.0.12-jdk-slim-bullseye 
RUN apt-get update && \
    apt-get install --yes --no-install-recommends curl junit4 && \
    sh -c "$(curl --location https://taskfile.dev/install.sh)" -- -d && \
    apt-get remove curl --yes && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY ../. /app

CMD task test-run

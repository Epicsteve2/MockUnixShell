FROM openjdk:11-jdk-slim-bullseye as install
RUN apt-get update && \
    apt-get install --yes --no-install-recommends \
        junit4 \
        make && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY ../. /app
RUN make test-compile

FROM openjdk:11-jre-slim-bullseye
RUN apt-get update && \
    apt-get install --yes --no-install-recommends \
        junit4 \
        make && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY --from=install /app /app

CMD ["make", "test-run", "--assume-old", "test-compile"]


# MockUnixShell
<p align="center">
  <img src="https://raw.githubusercontent.com/Epicsteve2/MockUnixShell/main/JShell.jpeg" alt="JShell">
</p>

The group project I worked on in CSCB07 during Fall 2020 at UTSC. It was worked on in a group of four over the course of five weeks.

# Requirments
The project was compiled using `Java OpenJDK 11`

The test files were run on `JUnit 4.11`

# Running the project
There are 3 methods to run the app

<!-- This project uses [go-task](https://taskfile.dev/), which is a modern replacement for GNU Make -->

1. ```make run-jar```: Generates a jar file and executes it
2. ```make run-class```: Compiles to JVM byte code and runs it
3. ```make run-docker```: Builds a docker image and runs it
  
Or you can run `driver/JShell.java` in a Java IDE

Usable commands are provided in `commands.pdf`

# Tests
1. ```make tests-run```: Runs all tests locally
2. ```make test-docker```: Builds a docker image and runs tests in it

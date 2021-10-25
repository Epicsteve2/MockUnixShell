JAR_FILE = mock_unix_shell.jar
IMAGE_NAME = mockunixshell
TEST_IMAGE_NAME = mockunixshell-tests

# There's definitely a better way.
JAVA_FILES = commands/*.java driver/*.java Exceptions/*.java fileSystem/*.java inputOutput/*.java
JAVA_CLASSES = commands/*.class driver/*.class Exceptions/*.class fileSystem/*.class inputOutput/*.class

.PHONY: clean
clean:
	find . \
		-type f \
		-name "*.class" \
		-exec \
			rm --verbose --force {} \;
	rm --verbose --force $(JAR_FILE)

.PHONY: compile
compile:
	javac -verbose -Xlint:unchecked $(JAVA_FILES)

.PHONY: build-jar
build-jar: | compile
	jar cvfe $(JAR_FILE) driver.JShell $(JAVA_CLASSES)

.PHONY: docker-build
docker-build:
	docker build \
		--tag $(IMAGE_NAME) \
		.

.PHONY: docker-test
docker-test:
	docker build \
		--file test/Dockerfile \
		--tag $(TEST_IMAGE_NAME) \
		.

.PHONY: run-jar
run-jar: | build-jar
	java -jar $(JAR_FILE)

.PHONY: run-class
run-class: | compile
	java driver.JShell

.PHONY: run-docker
run-docker: | docker-build
	docker run \
		--interactive \
		$(IMAGE_NAME)

.PHONY: bash-into-docker
bash-into-docker: | docker-build
	docker run \
		--interactive \
		--tty \
		--rm \
		--entrypoint bash \
		$(IMAGE_NAME)

.PHONY: bash-into-test
bash-into-test: | docker-test
	docker run \
		--interactive \
		--tty \
		--rm \
		--entrypoint bash \
		$(TEST_IMAGE_NAME)

.PHONY: test-compile
test-compile: | compile
	javac \
		-verbose \
		-Xlint:deprecation \
		-classpath .:/usr/share/java/junit4.jar \
		test/*.java
		

# .PHONY: test-jar
# test-jar: | test-compile
# 	jar cvfe test.jar org.junit.runner.JUnitCore $(JAVA_CLASSES) test/*.class

.PHONY: test-run
test-run: | test-compile
	ls test/ \
    	| grep --invert-match \
			--regexp "Dockerfile" \
			--regexp "\.class$$" \
			--regexp "^Mock" \
    	| xargs --replace="{}" --max-args=1 basename {} .java \
    	| sed --expression 's/^/test./' \
    	| tr '\n' ' ' \
    	| xargs --verbose java -classpath .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore

.PHONY: test-docker
test-docker: | docker-test
	docker run $(TEST_IMAGE_NAME)

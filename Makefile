
default: compile test run

compile:
	sbt compile -deprecation

test:
	sbt test

run:
	sbt "run input.txt"

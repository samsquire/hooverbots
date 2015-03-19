
default: compile test run

compile:
	sbt compile

test:
	sbt test

run:
	sbt "run input.txt"

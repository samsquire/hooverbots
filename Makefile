
default: compile test

compile:
	sbt compile

test:
	sbt test

run:
	sbt "run input.txt"

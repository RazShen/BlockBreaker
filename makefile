# 311130777
# shenkmr

compile: bin
	javac -d bin -cp biuoop-1.4.jar src/*.java src/*/*.java
jar: compile
	jar cfm ass6game.jar Manifest.mf -C bin . -C resources .
run: jar
	java -jar ass6game.jar
bin:
	mkdir bin


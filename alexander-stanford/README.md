# alexander-stanford

This is a subproject of alexander, providing a http-wrapper around the [Stanford Parser](https://nlp.stanford.edu/software/lex-parser.shtml).

## Build

Run

	mvn package
	
An uberjar called alexander-stanford-<version>-swarm.jar will be created.

## Running

You can start the uberjar with:

	java -jar alexander-stanford-<version>-swarm.jar
	
The service is available under http://localhost:8080/rest/parser/ner.
	
## Testing

JUnit tests are automatically performed during the build.
	
## Logging

alexander-stanford uses [Log4j2](https://logging.apache.org/log4j/2.x/) for logging.
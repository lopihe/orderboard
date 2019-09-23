#Overview
This is a solution for the "Live Order Board" coding exercise.

#Requirements
- Application: Java 11
- Unit tests: Groovy, Spock 

#Design decisions
The classes Order and OrderDetails have been separated as they encapsulate different concerns, reusing an Order as part of the summary would expose fields that should not be exposed.

For the sake of simplicity Order and OrderDetails have static factory methods, but later could be refactored to use the Builder pattern as optional fields and visibility become a problem.

The OrderBoard has the summary calculation logic at the moment (again, being pragmatic), but that could be extracted and supplied via a Strategy.

 
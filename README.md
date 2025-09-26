# mb-testframeworks-java

***

## Description

This test framework exists for demonstration and reference. It makes use of:

- java 17
- maven
- cucumber
- spring
- junit
- assertj
- generated openapi clients
- selenium webdriver
- allure reports

## Installation

### Prerequisites

- JDK 17, maven 3.x.x and IntelliJ are installed on your local pc.

### Compile

Building can be done using

`mvn clean compile test-compile`.

### Running tests

Before running tests, make sure to install the following recommended plugins (all by jetbrains):

- cucumber for java (by jetbrains)
- gherkin
- test automation

## Usage

### Run unit tests

Run unit tests using the context menu of the unit test file or using

`mvn clean test -Punit`

### Run with maven profile

When running directly using maven, make sure to use the right maven profile, e.g.

`mvn clean test -Pintegrated`.

`mvn clean test -Pintegrated-custom`.
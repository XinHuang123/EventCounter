# Project Title

Event Counter

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

<!-- for 30s window size-->
EventCounter counter = new EventCounter(300);
counter.open(); 
counter.logEvent(); 
counter.countEvent(30); 
counter.close(); 

### Prerequisites
Install Java and maven

### Installing
Clone the project into local environment and run maven command line in terminal mvn package

## Running the tests
Plz see the test cases

## Built With

Maven version:
Maven home: /usr/local/Cellar/maven/3.6.3_1/libexec
Java version: 13.0.2, vendor: N/A, runtime: /usr/local/Cellar/openjdk/13.0.2+8_2/libexec/openjdk.jdk/Contents/Home
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.15.5", arch: "x86_64", family: "mac"

openjdk version "13.0.2" 2020-01-14
OpenJDK Runtime Environment (build 13.0.2+8)
OpenJDK 64-Bit Server VM (build 13.0.2+8, mixed mode, sharing)

## Contributing

Please read [CONTRIBUTING.md](https://mkyong.com/maven/how-to-create-a-java-project-with-maven/) for details on how to install maven and run maven project

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details


Purpose of this project
=======================
This Java/Gradle based repository illustrates a selection of Java-based
microframeworks. 
 
It implements a functionally identical grocery list application with [Spark](http://sparkjava.com/),
[Ninja](http://http://www.ninjaframework.org/), [Ratpack](http://ratpack.io/) and [Jodd](http://jodd.org/)
is meant as supplement for my article 
on [heise developer online](http://www.heise.de/developer/).

![Grocery list overview](doc/overview.png)
![Grocery list edit](doc/edit.png)


Prerequisites
==============
- Installed JDK 8+ with proper set `JAVA_HOME` environment variable

This repository comes with a pre-bundled [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html)
`gradlew` or `gradlew.bat` (Windows) which will download the appropriate build dependencies automatically on the first 
run. 

*Optional (recommended for development):*
- [Gradle 2.8+](http://gradle.org/) 
- [Lombok](http://projectlombok.org/) Plugin for your IDE of choice (Preferably: IntelliJ IDEA UE)
- IntelliJ IDEA (Ultimate Edition) 


Spark Java
==========
![Spark Logo](http://sparkjava.com/img/logo.svg)

[Spark](http://sparkjava.com/) is a tiny Sinatra inspired framework for 
creating web applications in Java 8 with minimal effort

Starting the demo application
-----------------------------

### Option #1: Using Gradle
Run the demo with `gradle :spark:run` and access [http://localhost:8080/](http://localhost:8080/)

### Option #2: Using IntelliJ IDEA
Open the project using IntelliJ IDEA Ultimate and execute the _Spark: Run Example_ run configuration.

### Option #3: As standalone JAR
Let gradle build the all-in-one UberJAR and directly execute it with Java (preferrably in the `spark/`
directory to leverage from the example `grocerylists.json`.

     gradlew :spark:shadowJar
     cd spark
     java -jar build/libs/spark-1.0-SNAPSHOT-all.jar
      

Ninja
=====
![Ninja Logo](http://www.ninjaframework.org/ninja_logo.png)
      
[Ninja](http://http://www.ninjaframework.org/) is a opinionated, full stack framework including 
dependency injection, a optimized development mode and support for relational DBs.

Starting the demo application
-----------------------------

### Option #1: Using Gradle
Run the demo with `gradle :ninja:run` and access [http://localhost:8080/](http://localhost:8080/) 
and [http://localhost:8080/hello](http://localhost:8080/hello)

### Option #2: Using IntelliJ IDEA
Open the project using IntelliJ IDEA and execute the _Ninja: Run Example_ run configuration.

### Option #3: As standalone JAR
Let gradle build the all-in-one UberJAR and directly execute it with Java (preferrably in the `ninja/`
directory to leverage from the example `grocerylists.json`.

     gradlew :ninja:shadowJar
     cd ninja
     java -jar build/libs/ninja-1.0-SNAPSHOT-all.jar

      
      
Ratpack
=======
![Ratpack Logo](https://rawgit.com/ratpack/ratpack/v1.1.1/ratpack-manual/src/assets/images/ratpack-logo.png)
      
[Ratpack](http://ratpack.io/) is a reactive-oriented, type-safe microframework heavily based on Java 8 lambdas
and Netty for non-blocking IO.

Starting the demo application
-----------------------------

### Option #1: Using Gradle
Run the demo with `gradle :ratpack:run` and access [http://localhost:5050/](http://localhost:5050/).

### Option #2: Using IntelliJ IDEA
Open the project using IntelliJ IDEA and execute the _Ninja: Run Example_ run configuration.

### Option #3: As standalone JAR
Let gradle build the all-in-one UberJAR and directly execute it with Java (preferrably in the `ratpack/`
directory to leverage from the example `grocerylists.json`.

     gradlew :ratpack:shadowJar
     cd ratpack
     java -jar build/libs/ratpack-1.0-SNAPSHOT-all.jar      



Jodd
=======
![Jodd Logo](https://rawgit.com/oblac/jodd/master/src/site/resources/images/jodd.png)
      
[Jodd](http://jodd.org/) is set of Java micro frameworks, tools and utilities, under 1.5 MB.
The framework consists of micro components, which can be used more or less independently.

                         

Starting the demo application
-----------------------------

### Option #1: Using Gradle
Run the demo with `cd jodd` and `gradle jettyRun` and access [http://http://localhost:8080/jodd/](http://http://localhost:8080/jodd/).

### Option #2: Using IntelliJ IDEA
This example has no embedded servlet container bundled. To run you need to download a Jetty 8 container
and use the commericial IntelliJ IDEA Ultimate edition.
Open the project and configure the _jodd: Run in Jetty_ run configuration. Check for a proper reference to
a valid Jetty 8 installation under _Application Server_, then run the configuration.

### Option #3: As deployable WAR
This module does not provide a all-in-one UberJAR. Following how to build a WAR file:

     gradlew :jodd:war
     cp jodd/build/libs/jodd-1.0-SNAPSHOT.war [tomcat]/webapps

# Development Activity Dashboard

This is a java project that uses GitHub api to retrieve data about a repository and index it into elastic search.

This is the first commit for the project, default values are set to look at terraform repo for building the project, api key must be added.

Start by building a docker instance of elastic search, this can be done by running curl -fsSL https://elastic.co/start-local | sh

Import the project by using the pom.xml, this will build the dependencies

To run the project, add the GitHub owner, repo and api key variables to the AppRunner constructor method then run ./mvnw spring-boot:run

You can also build the jar file using ./mvnw clean package

To run the jar file, use: java -jar target/activitydashboard-0.0.1-SNAPSHOT.jar

Once the Jar file is built, you can also pass the variables in as args instead of adding them to AppRunner but they must be added initially to have the program pass tests and build: java -jar target/asyncmethod-0.0.1-SNAPSHOT.jar {owner} {repo} {api key}

Next step is adding tests and allowing you to pass those values as args when building the project.

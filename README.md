# Development Activity Dashboard

This is a java project that uses GitHub api to retrieve data about a repository and index it into elastic search.

This is the first commit for the project, default values are set to look at terraform repo for building the project, api key must be added.

Start by building a docker instance of elastic search, this can be done by running curl -fsSL https://elastic.co/start-local | sh in a git bash terminal for windows

Import the project by using the pom.xml, this will build the dependencies

To run the project, add the GitHub owner, repo and api key variables to the AppRunner constructor method then run mvn spring-boot:run

You can also build the jar file using mvn clean package

To run the jar file, use: java -jar ./target/activitydashboard-0.0.1-SNAPSHOT.jar

Once the Jar file is built, you can also pass the variables in as args instead of adding them to AppRunner but they must be added initially to have the program pass tests and build: java -jar target/asyncmethod-0.0.1-SNAPSHOT.jar {owner} {repo} {api key} {localhost url} {page count}
page count refers to the number of pages returned from GitHub api, records per page have been set to 100, this cna be changed in the GitHubLookupService constructor

Next step is adding tests and allowing you to pass those values as args when building the project.
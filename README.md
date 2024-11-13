# Development Activity Dashboard

This is a java project that uses GitHub api to retrieve data about a repository and index it into elastic search.

This is the first commit for the project, default values are set to look at terraform repo for building the project, api key must be added.

Start by building a docker instance of elastic search, this can be done by running curl -fsSL https://elastic.co/start-local | sh in a git bash terminal for windows

Import the project by using the pom.xml, this will build the dependencies

To run the project, create.env in main/java/resources, example provided, and add the localhost address, GitHub owner, repo, api key and pages variables to the .env then run mvn spring-boot:run

You can also build the jar file using mvn clean package, still requires the env to be set to build frist time

To run the jar file, use: java -jar ./target/activitydashboard-0.0.1-SNAPSHOT.jar

Once the Jar file is built, you can also pass the variables in as args instead of adding them to .env: java -jar target/activitydashboard-0.0.1-SNAPSHOT.jar {owner} {repo} {api key} {localhost url} {page count}
page count refers to the number of pages returned from GitHub api, records per page have been set to 100, this cna be changed in the GitHubLookupService constructor
When passing in args, all values must be added otherwise it will default to what is in the .env

# Example Kibana Dashboard

Shows number of pull requests, issues and releases against created_at date
Diagram like this can be created by importing ﻿"created_at per 7 days","Top 6 values of type.keyword","Count of records" and changing breakdowns
![image](https://github.com/user-attachments/assets/70dcd513-ad25-4e97-a028-47109a0f2219)

Many values are not as useful for visualisation such as title and body fields but I kept them in there as you may want to search for specific fields such as Feat and Fix if the repository is using convetional commits

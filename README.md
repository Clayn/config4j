# config4j

![Java CI](https://github.com/Clayn/config4j/workflows/Java%20CI/badge.svg) 
[![Build Status](http://www.clayncraft.de:8080/buildStatus/icon?job=Config4J%2Fmaster)](http://www.clayncraft.de:8080/job/Config4J/job/master/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Config4J is a simple and intuitive library to manage your needs of configuring your application.

You will get help with loading and storing your configurations as well as providing a default configuration

## Get Config4J

Config4J is currently build with Java 1.8 and available as maven dependency. 
Currently it is only hosted in the GitHub repository

````
<dependency>
  <groupId>de.clayntech</groupId>
  <artifactId>config4j</artifactId>
  <version>0.1.1</version>
</dependency>
````

### Usage

Config4J is designed with two usage concepts in mind.

1. Global/static accessed configuration
2. Individual configurations

#### Global Accessed Configuration

By using the Config4J class your application can access one central managed configuration. This configuration 
is provided by a ConfigurationProvider and should be configured very early during the startup process. 
You than have one central configuration that you can easily access from everywhere in your application. 


#### Individual Configurations

In contrast to the above concept you can give different parts of your application different configurations. 
These may be also provided by a ConfigurationProvider but can be managed in many different ways that suites your use case 
the best. You may have a plugin system and want to give each plugin a configuration that can be tempered with by other plugins. 

#### How to start

Do you have added the dependency for Config4J? Well you are now ready to go just by using 

````java
Config4J.getConfiguration()
````

with the default settings, a configuration file will be created (if it does not exist yet) 
at 
````
System.getProperty("user.dir")/application.config4j.properties
````
which is in the standard properties file format. 

Of course it won't have any settings yet but you can use that configuration and save it.

#### Future

Some features planned for future releases:

* Spring support
* Configuration of objects
    * Using field annotations
    
#### Contributing

Feel free to create new issues if you find any bugs or have ideas for wonderful features. 

Of course you can also create PRs to contribute to the code
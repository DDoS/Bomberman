# Bomberman #

ECSE 321 group project

## Building ##

### Tools ###
- Java 7 JKD
- Maven 2
- Recommended IDE: Intellij IDEA Community Edition

### Commands ###

Build JAR and execute:

    mvn

Build JAR:

    mnv install

Execute JAR:

    mvn exec:java

Delete all generated files:

    mvn clean

## Commit guidelines ##

- Don't commit if it doesn't compile
- Commit any changes that break features or otherwise reduce functionality to a new branch
- First 100 characters of the commit message should be a short summary of changes
- Format code before commiting
- Document all code and update docs where needed

## Formatting guidelines ##

On Windows, please change your git config using

    git config --global core.autocrlf true

to enable normalized line endings.

We'll borrow (with permission) the formatting settings from another project
- IDEA [formatter](doc/spout_formatting/IntelliJ/Spout.xml)
- Eclipse [formatter](doc/spout_formatting/Eclipse/formatter.xml) and [cleanup](doc/spout_formatting/Eclipse/cleanup.xml)

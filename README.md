# RpgInventorySystem
System for representing a inventory for RPGs. Written as an assignment for CSYE 6200.

# Installation
In order to run this project, initially, the Java FX library should be installed as user library named `javafx`.
### Steps for creating a user library for JavaFX
1. Go to Eclipse -> Preferences -> Java -> Build Path -> User Libraries
2. Add a new "User Library" give the name as `javafx`.
3. Click on "Add external JARs" and select all `.jar` from "lib" directory of downloaded JavaFX SDK.


Next step is to configure the JavaFx project for this project.

### Steps for creating a user library for JavaFX
1. Right click on the project folder -> Properties -> Java Build Path
2. Go to Libraries tab -> Click on "ModulePath" -> select "Add library" -> Choose "User Library" -> Choose `javafx` -> Apply and Close.

The last step is to set arguments for running the project.

### Steps for creating a user library for JavaFX
1. Right click on "Main.java" -> Click on "Run As" -> Click "Run Configurations"
2. In the `(x) = Arguments` tab -> Go to VM Arguments-> Type the following command: `--add-modules javafx.controls,javafx.fxml`
 

Finally, to run the program run application.main as a JavaFX program. 

# Testing
There are two types of tests used in this repository, JUnit tests, and manual tests implemented before we configured JUnit.

## JUnit Tests
Our JUnit tests can be run with JUnit 4. In eclipse this is done by selecting "Run As" -> "JUnit Test".

## Manual Tests
For tests implemented before we configured JUnit they are run as java applications.
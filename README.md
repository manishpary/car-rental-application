# car-rental-application
Tool for calculating Trip Expense.

# Prerequisites
## To Build
- Java 8

# How to Build
The project can be built using the included gradle wrapper by running the following command:
```
./gradlew build
```
# How to Test
## Unit
Unit Tests are run using the following command
```
./gradlew test
```
To view the test report,Run above command and go to this path /build/reports/index.html

## Sample Report 
Screenshot 2021-04-24 at 23.42.03![image](https://user-images.githubusercontent.com/39412150/115974823-7e9eb480-a557-11eb-9e0d-7590c4c0c47d.png)

# How to Run

## From JAR
Download Jar from the jar folder and run below command.

java -jar car-rental-api-0.0.1-SNAPSHOT.jar --car.rental.vehicltype=car --car.rental.fueltype=DIESEL --car.rental.destination=chennai
 --car.rental.nooftraveller=10 --car.rental.ac=true
 
## Sample Output

Screenshot 2021-04-24 at 23.40.47![image](https://user-images.githubusercontent.com/39412150/115974750-d7ba1880-a556-11eb-8a9f-e2719f9294ac.png)

## From Intellij
Import this project in intellij and run via Run Configuration.
 

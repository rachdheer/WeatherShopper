# Welcome to the Project "Weather Shopper Web Application"

# This is a MAVEN project integrated with TestNG using Selenium with JAVA

This project is build and taken care of below points 
  - Independent of Operating System
  - Test can run in Parallel and Single browser
  - Supports Chomre and Firefox 

This Project configurations are below.

  - IDE- Eclipse
  - Selenium-java-  3.141.59 
  - Browsers -
      - Chrome - 97
      - Firefox - 96

-Drivers - 
  - Chrome Driver - 97 
  - Geko Driver - v30

Prerequisites
  - Install Java and JDK
  - Install Selenium (zip file is added to the project)
  - Install Maven and set home paths 
  
  
The Projest Structure is below 

![Screen Shot 2022-01-24 at 11 48 54 PM](https://user-images.githubusercontent.com/88575599/150816111-4a27e88d-1cb5-4ec8-8c51-207b7bca19f2.png)

Under src/main folder 
- We have 3 class files 
    - Home Page
    - Checkout Page
    - Common Methods Page
    
  All the neccesary page objects and related methods are added to the repspective pages.
    
The Testscript is designed under the folder src/test 

  - SeleniumTest class is having covered all the steps of the web application.
  - SeleniumTest which extends TestBase 
  - TestBase class is defined and resposible for checking Operating system, checking browser, launching application, and tear down 
  


****** HOW TO RUN THE PROJECT ******

STEP 1

  Downlaod the prject to the desired loaction
  
Step 2 

  open command promt and navigate to the project directory 
  
Step 3
  
  execute below commands
  
  The below commands must be executed in the directory which contains the relevant pom file (go to the project directory where you downloaded)

  - execute - mvn clean
  - execute - mvn compile
  - mvn test - this command execute the tets in parallel with chrome and firefox browsers (pom.xml is set default to run test in parallel)
  
To run Test in Chorme browser 

  - have to open the project in a any IDE 
  - Find the testng_Chrome.xml file under the prject and 
  - Right click on the file 
  - Run as Tetsng Suit
  
To run Test in Fireofx browser 

  - have to open the project in a any IDE 
  - Find the testng_Firefox.xml file and 
  - Right click on the file 
  - Run as Tetsng Suit
  
The results are generated under the target folder of the project 

 - path - project/target/surefire-reports/
      - index.html - Basic testng report 
      
In detail Individual browser test reports are under the TestSuite folder 
   
 - path - project/target/surefire-reports/TestSuite/
    - ChromeTest.html
    - FirefoxTest.html

  NOTE: I have commented the code which maximise the browser to see the parallel execution in browser.
        The respective code is in the TestBase class, line number 84

 
  **** The End *******
  
  # Happy Testing 
  






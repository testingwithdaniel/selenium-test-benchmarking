# selenium-test-benchmarking

 - Uses Page Objet Model and TESTNG
 - Results are written to table `RegressionTests`

### Table creation query:
`CREATE TABLE RegressionTests (
    srNo int NOT NULL AUTO_INCREMENT,
    sessionID varchar(30),
    startTime DATETIME,
    endTime DATETIME,
    totalTime varchar(9),
    os varchar(10),
    os_version varchar(20),
    browser varchar(20),
    browser_version varchar(10),
    isException varchar(1),
    lastModified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    primary key (srNo)
);`

### Steps to setup this repo in Jenkins
- Create a new Maven job
- Configure the job to clone this repo
- Check 'Build Periodically' option and enter value `H 7 * * *`. This will run the job 7AM everyday
- Under Pre-Build steps, do `mvn compile`
- Under 'Build' > 'Goals and options', specify `test -P parallel`

### Review test results:
- Results are posted to the table RegressionTests under Database selenium_tests
- Queries to review results:
  - use selenium_tests
  - select * from RegressionTests;
- Modify the select query based on requirements to filter data
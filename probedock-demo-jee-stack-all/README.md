# probedock-demo-jee-stack-api

> This demo project offer a simple Object oriented implementation of a calculator with only the four basic operations. As it is a Java EE application, the operation are logged in a Derby database to offer a persistence layer for the demonstration. A REST API offer the possibility to submit operation to solve and another one to retrieve the logged operations submitted.

> In this last version of the project, we want to integrate all the different type of tests in only one project and to be sure we can make the difference between them. Remember we have the `unit`, `integration` and `api` tests which are all based on JUnit framework.

## Requirements

* Java 7+
* Maven 3.1

## Usage

1. Run the tests via maven with `mvn clean test` in project folder. The API tests present in the project will be run.

  If Probe Dock RT is running, you should see the test results in the tool. In fact, only the unit tests are run. For the default behavior, we believe that the developers want a quick test run and then the unit is quite reasonable. We have created different profiles in the `pom.xml` to manage the other test categories.

2. If not done, make sure you already done the step 2 to 4 from the [second project](../probedock-demo-jee-stack-unit).

3. To make sure that the different categories of tests are run when we want, we have done some configuration in the `surefire` maven plugin. We have updated the main configuration of surefire in this way:

  ```xml
  ...
  <plugin>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.19</version>
    <configuration>
      <includes>
        <!-- We use a feature of Surefire available since 2.18 which allow to
             negate a regular expression. Therefore, we want to execute all the
             tests excepted the ones present in the package integration or
             api. -->
        <include>!%regex[.*/integration/.*],!%regex[.*/api/.*]</include>
      </includes>
      <properties>
        <property>
          <name>listener</name>
          <value>io.probedock.client.junit.ProbeListener</value>
        </property>
      </properties>
    </configuration>
  </plugin>
  ...
  ```

  Then we added a profile for the `integration` tests:

  ```xml
  <profiles>
    <profile>
      <id>integration</id>
      <build>
        <plugins>
          <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <configuration>
              <includes>
                <!-- This time, we want to execute only the tests present
                     in the package integration and nothing else. -->
                <include>%regex[.*/integration/.*]</include>
              </includes>
            </configuration>
          </plugin>
        </plugins>
      </build>
    </profile>
    ...
  </profiles>
  ```

  We did the same for the `API` tests:

  ```xml
  <profiles>
    ...
    <profile>
      <id>api</id>
      <build>
        <plugins>
          <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <configuration>
              <includes>
                <!-- Only the tests present in the package api
                     will be executed. -->
                <include>%regex[.*/api/.*]</include>
              </includes>
            </configuration>
          </plugin>
        </plugins>
      </build>
    </profile>
    ...
  </profiles>
  ```

  And finally, a profile to rule them all:

  ```xml
  <profiles>
    ...
    <profile>
      <id>all</id>
      <build>
        <plugins>
          <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <configuration>
              <includes>
                <!-- All the tests are executed. -->
                <include>%regex[.*]</include>
              </includes>
            </configuration>
          </plugin>
        </plugins>
      </build>
    </profile>
  </profiles>
  ```

4. Now, we have configured the `probedock.yml` file to have the categories by package for `integration` and `API` tests. For all other tests, the default category is fine.

  ```yml
  ...
  java:
    categoriesByPackage:
      io.probedock.demo.jeestack.integration.*: Arquillian
      io.probedock.demo.jeestack.api**: API
  ```

5. To run the tests, we have the following commands:

  * `mvn clean test` to execute the `unit` tests;
  * `mvn clean test -Pintegration` to execute the `integration` tests;
  * `mvn clean test -Papi` to execute the `API` tests;
  * `mvn clean test -all` to execute all the tests.

6. Now you can play a bit with the tests. Break some tests and see the results on both interfaces. Play with the @ProbeTest and @ProbeTestClass annotations.

  You have a project with full setup to write all kind of tests excepted the end to end tests that are more suitable for application with a web user interface.

  Do not hesitate to read the documentation of [Probe Dock Junit](https://github.com/probedock/probedock-junit) and [Probe Dock RT Arquillian Extension](https://github.com/probedock/probedock-rt-arquillian-extension) for more details about the probes used to send the test results. You have also several information on the library used by both probes. Finally, you will find the list of probes and libraries on these pages: [Probe Dock Probes](https://github.com/probedock/probedock-probes) and [Probe Dock RT Probes](https://github.com/probedock/probedock-rt-probes).

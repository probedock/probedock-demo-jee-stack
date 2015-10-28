# probedock-demo-jee-stack-unit

> This demo project offer a simple Object oriented implementation of a calculator with only the four basic operations. As it is a Java EE application, the operation are logged in a Derby database to offer a persistence layer for the demonstration. A REST API offer the possibility to submit operation to solve and another one to retrieve the logged operations submitted.

> In this second version of the project, the Unit tests are implemented and integrated with [Probe Dock](https://github.com/probedock/probedock) and [Probe Dock RT](https://github.com/probedock/probedock-rt).

## Requirements

* Java 7+
* Maven 3.1

## Usage

1. Run the tests via maven with `mvn clean test` in project folder. The Unit tests present in the project will be run.

  If Probe Dock RT is running, you should see the test results in the tool.

2. Now you can continue to the Probe Dock setup. If already registered, skip this step. Do the registration on the demo server and create your account. You will receive an email confirmation with a link.

3. Create the project in your organization on Probe Dock. First, access the projects' page and click on the Add a new project button. You can use the name jee-stack-sandbox for example for both name and display name. You can leave the description empty for the demo.

4. Once the project is created on Probe Dock, you should follow the Getting started guide to setup your project. The guide will invite you to setup your main configuration file and the project configuration file. For this project, you must place the file probedock.yml in <projectRootFolder>/src/test/resources/.

5. Now, you can run the tests again via maven with `mvn clean test`. In addition of the test results shown in Probe Dock RT, you will also see results on Probe Dock on the dashboard. You are able to see the result details by clicking on the report line.

6. Now you can play a bit with the tests. Break some tests and see the results on both interfaces. Play with the @ProbeTest and @ProbeTestClass annotations. You can also read the documentation of [Probe Dock Junit](https://github.com/probedock/probedock-junit) and [Probe Dock RT Junit](https://github.com/probedock/probedock-rt-junit) for more details about the probes used to send the test results. You have also several information on the library used by both probes. Finally, you will find the list of probes and libraries on these pages: [Probe Dock Probes](https://github.com/probedock/probedock-probes) and [Probe Dock RT Probes](https://github.com/probedock/probedock-rt-probes).

## What have been done to integrate Probe Dock?

1. Introduced two new dependencies in `pom.xml`

  ```xml
    ...
    <dependencies>
      ...
      <!-- JUnit dependency -->
      <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
      </dependency>

      <!-- AssertJ to write better assertions -->
      <dependency>
        <groupId>org.assertj</groupId>
        <artifactId>assertj-core</artifactId>
        <version>2.2.0</version>
        <scope>test</scope>
      </dependency>

      <!-- Probe Dock and Probe Dock RT probes dependencies -->
      <dependency>
        <groupId>io.probedock.client</groupId>
        <artifactId>probedock-junit</artifactId>
        <version>0.3.0</version>
        <scope>test</scope>
      </dependency>
      <dependency>
        <groupId>io.probedock.rt.client</groupId>
        <artifactId>probedock-rt-junit</artifactId>
        <version>0.3.0</version>
        <scope>test</scope>
      </dependency>
      ...
    </dependencies>
    ...
  ```

2. We added the `Maven Surefire Plugin` with some configuration to run the tests with Probe Dock JUnit listeners.

  ```xml
  ...
  <build>
    ...
    <plugins>
      ...
      <plugin>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.19</version>
        <configuration>
          <properties>
            <property>
              <name>listener</name>
              <value>io.probedock.client.junit.ProbeListener,io.probedock.rt.client.junit.ProbeRTListener</value>
            </property>
          </properties>
        </configuration>
      </plugin>
      ...
    </plugins>
    ...
  </build>
  ...
  ```

3. We have written several unit tests in `src/test/java`.

4. Then you have added the `probedock.yml` file.

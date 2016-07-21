# probedock-demo-jee-stack-integration

> This demo project offer a simple Object oriented implementation of a calculator with only the four basic operations. As it is a Java EE application, the operation are logged in a Derby database to offer a persistence layer for the demonstration. A REST API offer the possibility to submit operation to solve and another one to retrieve the logged operations submitted.

> In this third version of the project, the Integration tests are implemented and integrated with [Probe Dock](https://github.com/probedock/probedock) and [Probe Dock RT](https://github.com/probedock/probedock-rt).

> We use the [Arquillian](http://arquillian.org) testing framework with [JUnit](http://junit.org/) implementation of the tests.

## Requirements

* Java 7+
* Maven 3.1

## Usage

1. Run the tests via maven with `mvn clean test` in project folder. The Integration tests present in the project will be run.

  If Probe Dock RT is running, you should see the test results in the tool.

2. If not done, make sure you already done the step 2 to 4 from the [second project](../probedock-demo-jee-stack-unit).

3. We want to let the test category of the tests run to appear as `Arquillian` and not `JUnit`. Then we will use a feature present in [Probe Dock Java](https://github.com/probedock/probedock-java) since version 0.4.0. We have the possibility to configure categories by package patterns. For that, we have to write the following configuration.

  ```yml
  ...
  java:
    categoriesByPackage:
      io.probedock.demo.jeestack.integration.*: Arquillian
  ```

  When a test is executed and is in a class in a sub-package of `io.probedock.demo.jeestack.integration`, the category will be `Arquillian`.

4. Now, you can run the tests again via maven with `mvn clean test`. In addition of the test results shown in Probe Dock RT, you will also see results on Probe Dock on the dashboard. You are able to see the result details by clicking on the report line.

5. Now you can play a bit with the tests. Break some tests and see the results on both interfaces. Play with the @ProbeTest and @ProbeTestClass annotations. You can also read the documentation of [Probe Dock Junit](https://github.com/probedock/probedock-junit) and [Probe Dock RT Arquillian Extension](https://github.com/probedock/probedock-rt-arquillian-extension) for more details about the probes used to send the test results. You have also several information on the library used by both probes. Finally, you will find the list of probes and libraries on these pages: [Probe Dock Probes](https://github.com/probedock/probedock-probes) and [Probe Dock RT Probes](https://github.com/probedock/probedock-rt-probes).

## What have been done to integrate Probe Dock?

1. Introduced two new dependencies in `pom.xml`

  ```xml
    ...
    <dependencies>
      ...
      <!-- JUnit dependency, same as Unit tests -->
      <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
      </dependency>

      <!-- AssertJ to write better assertions, same as Unit tests -->
      <dependency>
        <groupId>org.assertj</groupId>
        <artifactId>assertj-core</artifactId>
        <version>2.2.0</version>
        <scope>test</scope>
      </dependency>

      <!-- Probe Dock and Probe Dock RT probes dependencies -->
      <!-- The Probe Dock RT is different than for the Unit tests as it is an
           extension of Arquillian. Therefore, the Probe itself is a dependency of
           the extension. -->
      <dependency>
        <groupId>io.probedock.client</groupId>
        <artifactId>probedock-junit</artifactId>
        <version>0.3.0</version>
        <scope>test</scope>
      </dependency>
      <dependency>
  			<groupId>io.probedock.rt.client</groupId>
  			<artifactId>probedock-rt-arquillian-extension</artifactId>
  			<version>0.1.0</version>
  			<scope>test</scope>
  		</dependency>

      <!-- Arquillian dependencies to make Integration tests working with
           a Glassfish 4.1.1 Embedded Application Server. -->
  		<dependency>
  			<groupId>org.jboss.arquillian.junit</groupId>
  			<artifactId>arquillian-junit-container</artifactId>
  			<scope>test</scope>
  		</dependency>
  		<dependency>
  			<groupId>org.glassfish.main.extras</groupId>
  			<artifactId>glassfish-embedded-all</artifactId>
  			<version>4.1.1</version>
  			<scope>provided</scope>
  		</dependency>
  		<dependency>
  			<groupId>org.jboss.arquillian.container</groupId>
  			<artifactId>arquillian-glassfish-embedded-3.1</artifactId>
  			<version>1.0.0.CR4</version>
  			<scope>test</scope>
  		</dependency>
  		<dependency>
  			<groupId>org.slf4j</groupId>
  			<artifactId>slf4j-simple</artifactId>
  			<version>1.6.4</version>
  			<scope>test</scope>
  		</dependency>
  		<dependency>
  			<groupId>org.glassfish.jersey.core</groupId>
  			<artifactId>jersey-client</artifactId>
  			<version>2.4</version>
  			<scope>test</scope>
  		</dependency>
      ...
    </dependencies>
    ...
  ```

2. We also added a plugin management for Arquillian version management.

  ```xml
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.jboss.arquillian</groupId>
        <artifactId>arquillian-bom</artifactId>
        <version>1.1.10.Final</version>
        <scope>import</scope>
        <type>pom</type>
      </dependency>
    </dependencies>
  </dependencyManagement>
  ```

3. We added the `Maven Surefire Plugin` with some configuration to run the tests with Probe Dock JUnit listeners. The configuration is the same as we did for Unit tests.

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

4. We added several configuration files for Arquillian:

  * [arquillian.xml](src/test/resources/arquillian.xml) which tell Arquillian the Glassfish resources to configure;
  * [glassfish-resources.xml](src/test/resources/glassfish-resources.xml) that creates the JDBC resource for the persistence;
  * [test-persistence.xml](src/test/resources/test-persistence.xml) which is used to setup the persistence during tests execution.

5. We have written several integration tests in `src/test/java`. The tests required some Arquillian preparation. First, we needed to add the test runner from Arquillian:

  ```java
  @RunWith(Arquillian.class)
  public class ...
  ```

  Then we added a deployment method to Arquillian how to prepare the test run. This setup depends of the requirement for the test.

  ```java
  @Deployment
  public static JavaArchive createDeployment() {
    JavaArchive jar = ShrinkWrap
      // Create the Java Archive
      .create(JavaArchive.class)

      // Add the test persistence.xml for the persistence configuration
      .addAsManifestResource("test-persistence.xml", "persistence.xml")

      // Add the classes needed by the tests
      .addClass(OperationLog.class)
      .addClass(OperationLogDao.class)
      .addClass(OperationLogDaoImpl.class)

      // Add the beans.xml to enable the CDI
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

    return jar;
  }
  ```

6. Then you have added the `probedock.yml` file with the modification for the category.

# probedock-demo-jee-stack-api

> This demo project offer a simple Object oriented implementation of a calculator with only the four basic operations. As it is a Java EE application, the operation are logged in a Derby database to offer a persistence layer for the demonstration. A REST API offer the possibility to submit operation to solve and another one to retrieve the logged operations submitted.

> In this fourth version of the project, the API tests are implemented and integrated with [Probe Dock](https://github.com/probedock/probedock) and [Probe Dock RT](https://github.com/probedock/probedock-rt).

> We use a [custom API test framework](https://github.com/probedock/java-api-test) that wrap REST calls and several utility methods to help writing API tests.

## Requirements

* Java 7+
* Maven 3.1

## Usage

1. Run the tests via maven with `mvn clean test` in project folder. The API tests present in the project will be run.

  If Probe Dock RT is running, you should see the test results in the tool.

2. If not done, make sure you already done the step 2 to 4 from the [second project](../probedock-demo-jee-stack-unit).

3. We want to let the test category of the tests run to appear as `API` and not `JUnit`. We will do the same kind of configuration we did in the [Integration project](../probedock-demo-jee-stack-integration). We will use a feature present in [Probe Dock Java](https://github.com/probedock/probedock-java) since version 0.4.0. We have the possibility to configure categories by package patterns. For that, we have to write the following configuration.

  ```yml
  ...
  java:
    categoriesByPackage:
      io.probedock.demo.jeestack.api**: API
  ```

  When a test is executed and is in a class in the the package `io.probedock.demo.jeestack.api` or one of its sub-package, the category will be `API`.

4. Now, you can run the tests again via maven with `mvn clean test`. In addition of the test results shown in Probe Dock RT, you will also see results on Probe Dock on the dashboard. You are able to see the result details by clicking on the report line.

5. Now you can play a bit with the tests. Break some tests and see the results on both interfaces. Play with the @ProbeTest and @ProbeTestClass annotations.

  You have probably noticed that the setup for the API tests is really similar than the setup for the integration tests. We also use `Arquillian` to run the API tests.

  You can also read the documentation of [Probe Dock Junit](https://github.com/probedock/probedock-junit) and [Probe Dock RT Arquillian Extension](https://github.com/probedock/probedock-rt-arquillian-extension) for more details about the probes used to send the test results. You have also several information on the library used by both probes. Finally, you will find the list of probes and libraries on these pages: [Probe Dock Probes](https://github.com/probedock/probedock-probes) and [Probe Dock RT Probes](https://github.com/probedock/probedock-rt-probes).

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
      <dependency>
  			<groupId>org.apache.derby</groupId>
  			<artifactId>derbyclient</artifactId>
  			<version>10.12.1.1</version>
  			<scope>test</scope>
  		</dependency>

      <!-- The custom API test framework -->
      <dependency>
  			<groupId>io.probedock.api</groupId>
  			<artifactId>java-api-test</artifactId>
  			<version>1.0.0</version>
        <scope>test</scope>
  		</dependency>

      <!-- The custom data layer framework -->
      <dependency>
  			<groupId>io.probedock.test</groupId>
  			<artifactId>junitee-data-utils</artifactId>
  			<version>1.0.0</version>
  			<scope>test</scope>
  		</dependency>

      <!-- Data utilities to manage the persistence in the API tests -->
      <dependency>
        <groupId>io.probedock.test</groupId>
        <artifactId>junitee-data-utils</artifactId>
        <version>1.0.0</version>
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

5. We have written several API tests in `src/test/java`. The tests required some Arquillian preparation. First, we needed to add the test runner from Arquillian:

  ```java
  @RunWith(Arquillian.class)
  public class ...
  ```

  We added an URL property annotated with `Arquillian` annotation to retrieve the base URL. This allow us to do the REST API calls based on the URL that is different at each test execution.

  ```java
  @ArquillianResource
  private URL baseUrl;
  ```

  Then we added a deployment method to Arquillian how to prepare the test run. This setup depends of the requirement for the test. Note that we tell `Arquillian` that the deployment is not intended to run any tests in the application context. In fact, the part of application virtually deployed is prepared to accept HTTP requests.

  ```java
  // Tell Arquillian to not run any test in the context of this archive.
	@Deployment(testable = false)
	public static WebArchive createDeployment() {
    // We prepare a WEB archive
    WebArchive war = ShrinkWrap
			.create(WebArchive.class)

      // We add the application classes
			.addClass(OperationLog.class)
			.addClass(OperationLogDao.class)
			.addClass(OperationLogDaoImpl.class)
			.addClass(CalculatorService.class)
			.addClass(CalculatorServiceImpl.class)
			.addClass(OperationConverterService.class)
			.addClass(OperationConverterServiceImpl.class)

      // Do not forget to add the REST Application otherwise nothing is
      // exposed through the API.
			.addClass(ApiRestApplication.class)

      // Add the resource to test
			.addClass(CalculatorResource.class)

      // Add the configuration for the persistence
			.addAsResource("test-persistence.xml", "META-INF/persistence.xml")

      // Add the beans.xml to enable injection
			.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));

		return war;
	}
  ```

6. Then you have added the `probedock.yml` file with the modification the category.

7. We also did some setup for the `junitee-data-utils` framework. We added a specific `persistence.xml` in `test/resources/META-INF` to setup `Derby` connection for the tests in client mode.

  The `AbstractApiTest` is there to connect all the test frameworks used in this demo project.

  You can have a better understanding of the [junitee-data-utils](https://github.com/probedock/junitee-data-utils) for the persistence part and [java-api-test](https://github.com/probedock/java-api-test) for the JSON REST API testing.

# probedock-demo-jee-stack-notest

> This demo project offer a simple Object oriented implementation of a calculator with only the four basic operations. As it is a Java EE application, the operation are logged in a Derby database to offer a persistence layer for the demonstration. A REST API offer the possibility to submit operation to solve and another one to retrieve the logged operations submitted.

## Requirements

* Java 7+
* Maven 3.1

## Usage

1. Run the application via maven with `MAVEN_OPTS=-XX:MaxPermSize=256m mvn clean install` in project folder. This will start the embedded Glassfish Server.

**Note**: The MaxPermSize is to let Maven to run Glassfish Embedded and the demo application.

2. You will the possibility to send an operation on `POST http://localhost:8282/jee-stack/api/calculator`. There is an example of a sendable payload:

  ```json
  {
    "type": "add",
    "left": 2,
    "rightOperation": {
      "type": "sub",
      "left": 10,
      "rightOperation": {
        "type": "mul",
        "leftOperation": {
          "type": "div",
          "left": 15,
          "right": 3
        },
        "right": 4
      }
    }
  }
  ```

  The result of this operation should be:

  ```json
  {
    "result": -8
  }
  ```

  You can see that we have only `add`, `mul`, `div` and `sub` operations. An operation can be simple integers for `left` or `right` or it can be other operation as `leftOperation` and `rightOperation`.

3. You can retrieve the list of all submitted operations by calling `GET http://localhost:8282/jee-stack/api/calculator`. There is an example of the result retrieved:

  ```json
  [
    {
      "id": 1,
      "executionDate": 1445416786522,
      "result": -8,
      "operation": "( 2 + ( 10 - ( ( 15 / 3 ) * 4 ) ) )"
    }
  ]
  ```

4. So the application is not so complex but already contained:

  * Persistence layer (DAO)
  * Business services layer
  * Converter layer
  * Transfer objects (TO)
  * REST API layer

  And nothing is automated tested at this stage.

  **Note**: In this demo, we also re-use another demo. The [Probe Dock Demo JUnit](https://github.com/probedock/probedock-demo-junit). Therefore, the `Operation` part is already unit tested in this project.

5. The project [probedock-demo-jee-stack-unit](../probedock-demo-jee-stack-unit) will introduce the unit tests.

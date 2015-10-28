# probedock-demo-jee-stack

> Full demonstration of integration with different type of tests in a full Java EE stack.

> Unit, Integration and API tests are integrated with [Probe Dock](https://github.com/probedock/probedock) and [Probe Dock RT](https://github.com/probedock/probedock-rt).

> The project contains several projects which help to discover the path from no tests to all tests integrated with Probe Dock.

* `probedock-demo-jee-stack-notest` is the base for the projects with tests;
* `probedock-demo-jee-stack-unit` contains only unit tests and is integrated with Probe Dock;
* `probedock-demo-jee-stack-integration` contains only integration tests and is integrated with Probe Dock;
* `probedock-demo-jee-stack-api` contains only the API tests and is integrated with Probe Dock;
* `probedock-demo-jee-stack-all` contains all the three types of tests and is integrated with Probe Dock.

## Requirements

* Java 7+
* Maven 3.1
* Node.js 0.12+
* NPM 2.12+

## Usage

1. If not done, install and setup [Probe Dock RT](https://github.com/probedock/probedock-rt#installation).

2. Clone this repository.

3. Then, explore the different project to see how it is working. Each project contains some information about the integration of the testing technology used in the project.

  1. [probedock-demo-jee-stack-notest](probedock-demo-jee-stack-notest)
  2. [probedock-demo-jee-stack-unit](probedock-demo-jee-stack-unit)
  3. [probedock-demo-jee-stack-integration](probedock-demo-jee-stack-integration)
  4. [probedock-demo-jee-stack-api](probedock-demo-jee-stack-api)
  5. [probedock-demo-jee-stack-all](probedock-demo-jee-stack-all)

4. Now you can play a bit with the different projects. Break some tests and see the results on both interfaces. Play with the `@ProbeTest` and `@ProbeTestClass` annotations. You will find the list of probes and libraries on these pages: [Probe Dock Probes](https://github.com/probedock/probedock-probes) and [Probe Dock RT Probes](https://github.com/probedock/probedock-rt-probes).

## Contributing

* [Fork](https://help.github.com/articles/fork-a-repo)
* Create a topic branch - `git checkout -b feature`
* Push to your branch - `git push origin feature`
* Create a [pull request](http://help.github.com/pull-requests/) from your branch

Please add a changelog entry with your name for new features and bug fixes.

## License

**probedock-demo-jee-stack** is licensed under the [MIT License](http://opensource.org/licenses/MIT).
See [LICENSE.txt](LICENSE.txt) for the full text.

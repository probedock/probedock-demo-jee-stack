package io.probedock.demo.jeestack.api;

import io.probedock.api.test.client.ApiTestResponse;
import io.probedock.api.test.client.ApiUriBuilder;
import io.probedock.junitee.finder.Finder;
import io.probedock.demo.jeestack.model.OperationLog;
import io.probedock.demo.jeestack.persistence.OperationLogDao;
import io.probedock.demo.jeestack.persistence.OperationLogDaoImpl;
import io.probedock.demo.jeestack.rest.ApiRestApplication;
import io.probedock.demo.jeestack.rest.CalculatorResource;
import io.probedock.demo.jeestack.service.CalculatorService;
import io.probedock.demo.jeestack.service.CalculatorServiceImpl;
import io.probedock.demo.jeestack.service.OperationConverterService;
import io.probedock.demo.jeestack.service.OperationConverterServiceImpl;
import io.probedock.demo.jeestack.utils.finders.OperationLogFinder;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.*;

/**
 * Test class for {@link CalculatorResource}
 *
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
@RunWith(Arquillian.class)
public class CalculatorResourceTest extends AbstractApiTest {
	@ArquillianResource
	private URL baseUrl;

	private ApiUriBuilder apiUriBuilder;

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		WebArchive war = ShrinkWrap
			.create(WebArchive.class)
			.addClass(OperationLog.class)
			.addClass(OperationLogDao.class)
			.addClass(OperationLogDaoImpl.class)
			.addClass(CalculatorService.class)
			.addClass(CalculatorServiceImpl.class)
			.addClass(OperationConverterService.class)
			.addClass(OperationConverterServiceImpl.class)
			.addClass(ApiRestApplication.class)
			.addClass(CalculatorResource.class)
			.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
			.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));

		return war;
	}

	@Before
	public void setup() {
		apiUriBuilder = new ApiUriBuilder(baseUrl.toString()).path("api", "calculator");
	}

	@Test
	public void itShouldBePossibleToPostSimpleOperation() {
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
			.add("type", "add")
			.add("left", 1)
			.add("right", 2);

		ApiTestResponse response = postResource(jsonBuilder.build(), apiUriBuilder);

		assertThat(response.getStatus()).isEqualTo(200);

		JsonObject result = response.getResponseAsJsonObject();
		assertThat(result.getInt("result")).isEqualTo(3);
	}

	@Test
	public void itShouldBePossibleToPostComplexOperation() {
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
			.add("type", "add")
			.add("leftOperation",
				Json.createObjectBuilder()
					.add("type", "add")
					.add("left", 1)
					.add("right", 2)
			)
			.add("rightOperation",
				Json.createObjectBuilder()
					.add("type", "add")
					.add("left", 3)
					.add("right", 4)
			);

		ApiTestResponse response = postResource(jsonBuilder.build(), apiUriBuilder);

		assertThat(response.getStatus()).isEqualTo(200);

		JsonObject result = response.getResponseAsJsonObject();
		assertThat(result.getInt("result")).isEqualTo(10);
	}

	@Test
	public void anErrorShouldBeReturnedWhenOperationTypeIsUnknown() {
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
			.add("type", "unk")
			.add("left", 1)
			.add("right", 2);

		ApiTestResponse response = postResource(jsonBuilder.build(), apiUriBuilder);

		assertThat(response.getStatus()).isEqualTo(422);

		JsonObject result = response.getResponseAsJsonObject();
		assertThat(result.getString("message")).isEqualTo("The operation type unk is unknown.");
	}

	@Test
	public void anErrorShouldBeReturnedWhenTryingToDivideByZero() {
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
			.add("type", "div")
			.add("left", 1)
			.add("right", 0);

		ApiTestResponse response = postResource(jsonBuilder.build(), apiUriBuilder);

		assertThat(response.getStatus()).isEqualTo(422);

		JsonObject result = response.getResponseAsJsonObject();
		assertThat(result.getString("message")).isEqualTo("Cannot divide by zero.");
	}

	@Test
	// This annotation tell junitee-data-utils to manage the injection to make
	// the persistence available in the test method
	@Finder(OperationLogFinder.class)
	public void itShouldBePossibleToRetrieveAllTheOperationLogs() {
		// From the abstract api test, we retrieve the finder correctly injected to
		// get access to the persistence layer. Therefore, we can benefit the persistence
		// API outside a managed context like an application container or Arquillian.
		int size = finderManager.getFinder(OperationLogFinder.class).count();

		ApiTestResponse response = getResource(apiUriBuilder);
		assertThat(response.getResponseAsJsonArray().size()).isEqualTo(size);

		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
			.add("type", "add")
			.add("left", 1)
			.add("right", 2);

		postResource(jsonBuilder.build(), apiUriBuilder);

		response = getResource(apiUriBuilder);
		assertThat(response.getResponseAsJsonArray().size()).isEqualTo(size + 1);
	}
}

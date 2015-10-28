package io.probedock.demo.jeestack.api;

import io.probedock.api.test.client.IApiTestClientConfiguration;
import io.probedock.api.test.headers.IApiHeaderConfiguration;
import io.probedock.api.test.headers.IApiHeaderConfigurator;
import io.probedock.api.test.headers.IApiHeaderConfiguratorLocator;
import io.probedock.junitee.finder.FinderManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.rules.TestRule;

/**
 * Base implementation for the REST API tests
 * 
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
public abstract class AbstractApiTest extends io.probedock.api.test.AbstractApiTest{
	private static final Configuration CONFIGURATION = Configuration.getInstance();

	protected FinderManager finderManager;
	
	@Override
	protected void preBuild() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
		finderManager = new FinderManager(emf);
	}

	@Override
	protected List<TestRule> rulesAfterHeaderConfigurationRule() {
		return Arrays.asList(new TestRule[]{ finderManager });
	}
	
	@Override
	protected String getEntryPoint() {
		return "n/a";
	}
	
	@Override
	protected IApiTestClientConfiguration getClientConfiguration() {
		return CONFIGURATION;
	}

	@Override
	protected IApiHeaderConfiguratorLocator getHeaderConfiguratorLocator() {
		return new IApiHeaderConfiguratorLocator() {
			@Override
			public IApiHeaderConfigurator getHeaderConfigurator(Class<? extends IApiHeaderConfigurator> klass) {
				return new IApiHeaderConfigurator() {
					@Override
					public List<IApiHeaderConfiguration> getApiHeaderConfigurations() {
						return new ArrayList<>();
					}
				};
			}
		};
	}
}

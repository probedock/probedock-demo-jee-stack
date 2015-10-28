package io.probedock.demo.jeestack.integration.service;

import io.probedock.client.annotations.ProbeTest;
import io.probedock.client.annotations.ProbeTestClass;
import io.probedock.demo.jeestack.model.OperationLog;
import io.probedock.demo.jeestack.persistence.OperationLogDao;
import io.probedock.demo.jeestack.persistence.OperationLogDaoImpl;
import io.probedock.demo.jeestack.service.CalculationException;
import io.probedock.demo.jeestack.service.CalculatorService;
import io.probedock.demo.jeestack.service.CalculatorServiceImpl;
import io.probedock.demo.jeestack.service.OperationConverterService;
import io.probedock.demo.jeestack.service.OperationConverterServiceImpl;
import io.probedock.demo.jeestack.to.OperationTO;
import io.probedock.demo.jeestack.to.ResultTO;
import java.util.List;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.*;

/**
 * Integration tests for {@link CalculatorService}
 * 
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
@RunWith(Arquillian.class)
@ProbeTestClass(contributors = "laurent.prevost@probedock.io", tickets = "feature-calculator", tags = { "service", "operation" })
public class CalculatorServiceTest {

	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive jar = ShrinkWrap
			.create(JavaArchive.class)
			.addAsManifestResource("test-persistence.xml", "persistence.xml")
			.addClass(OperationLog.class)
			.addClass(OperationLogDao.class)
			.addClass(OperationLogDaoImpl.class)
			.addClass(CalculatorService.class)
			.addClass(CalculatorServiceImpl.class)
			.addClass(OperationConverterService.class)
			.addClass(OperationConverterServiceImpl.class)
			.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

		return jar;
	}

	@EJB
	private CalculatorService calculatorService;
	
	@EJB
	private OperationLogDao operationLogDao;
	
	@Test
	public void givenValidOperationItShouldProduceResultAndLogIsPersisted() throws CalculationException {
		List<OperationLog> logs = operationLogDao.findAll();
		
		OperationTO opto = new OperationTO("add", 5, 4);
		
		ResultTO result = calculatorService.process(opto);
		
		assertThat(result.getResult()).isEqualTo(9);
		
		List<OperationLog> newLogs = operationLogDao.findAll();
		assertThat(newLogs.size()).isEqualTo(logs.size() + 1);
		assertThat(newLogs.get(newLogs.size() - 1).getOperation()).isEqualTo("( 5 + 4 )");
	}
	
	@Test
	@ProbeTest(tags = "error")
	public void givenInvalidOperationAnExceptionIsThrown() {
		try {
			// Div by zero is forbidden
			calculatorService.process(new OperationTO("div", 1, 0));
			failBecauseExceptionWasNotThrown(CalculationException.class);
		}
		catch (CalculationException ce) {
		}
	}
}

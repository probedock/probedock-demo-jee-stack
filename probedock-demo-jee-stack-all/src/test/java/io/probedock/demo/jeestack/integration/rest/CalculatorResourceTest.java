package io.probedock.demo.jeestack.integration.rest;

import io.probedock.client.annotations.ProbeTestClass;
import io.probedock.demo.jeestack.model.OperationLog;
import io.probedock.demo.jeestack.persistence.OperationLogDao;
import io.probedock.demo.jeestack.persistence.OperationLogDaoImpl;
import io.probedock.demo.jeestack.rest.CalculatorResource;
import io.probedock.demo.jeestack.service.CalculatorService;
import io.probedock.demo.jeestack.service.CalculatorServiceImpl;
import io.probedock.demo.jeestack.service.OperationConverterService;
import io.probedock.demo.jeestack.service.OperationConverterServiceImpl;
import io.probedock.demo.jeestack.to.ErrorTO;
import io.probedock.demo.jeestack.to.OperationTO;
import io.probedock.demo.jeestack.to.ResultTO;
import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.*;

/**
 * Test class for {@link CalculatorResource}
 * 
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
@RunWith(Arquillian.class)
@ProbeTestClass(contributors = "laurent.prevost@probedock.io", tickets = "feature-rest", tags = { "rest", "operation" })
public class CalculatorResourceTest {
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
			.addClass(CalculatorResource.class)
			.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

		return jar;
	}
	
	@EJB
	private CalculatorResource calculatorResource;
	
	@Test
	public void itShouldBePossibleToSumbitSimpleOperation() {
		OperationTO op = new OperationTO("add", 1, 2);
		
		Response resp = calculatorResource.doOperation(op);
		assertThat(resp.getStatus()).isEqualTo(200);

		ResultTO result = (ResultTO) resp.getEntity();
		assertThat(result.getResult()).isEqualTo(3);
	}
	
	@Test
	public void itShouldBePossibleToSubmitComplexOperation() {
		OperationTO op = new OperationTO(
			"add",
			new OperationTO("add", 1, 2),
			new OperationTO("add", 3, 4)
		);
		
		Response resp = calculatorResource.doOperation(op);
		assertThat(resp.getStatus()).isEqualTo(200);

		ResultTO result = (ResultTO) resp.getEntity();
		assertThat(result.getResult()).isEqualTo(10);		
	}

	@Test
	public void anErrorIsReturnedWhenTheOperationIsUnknown() {
		Response resp = calculatorResource.doOperation(new OperationTO("unk", 1, 2));
		assertThat(resp.getStatus()).isEqualTo(422);
		
		ErrorTO err = (ErrorTO) resp.getEntity();
		assertThat(err.getMessage()).isEqualTo("The operation type unk is unknown.");
	}
}

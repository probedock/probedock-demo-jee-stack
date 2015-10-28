package io.probedock.demo.jeestack.integration.service;

import io.probedock.client.annotations.ProbeTest;
import io.probedock.client.annotations.ProbeTestClass;
import io.probedock.demo.jeestack.service.CalculationException;
import io.probedock.demo.jeestack.service.OperationConverterService;
import io.probedock.demo.jeestack.service.OperationConverterServiceImpl;
import io.probedock.demo.jeestack.to.OperationTO;
import io.probedock.demo.junit.Operation;
import io.probedock.demo.junit.OperationAdd;
import io.probedock.demo.junit.OperationDiv;
import io.probedock.demo.junit.OperationMul;
import io.probedock.demo.junit.OperationSub;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.*;

/**
 * Integration tests for {@link OperationConverterService}
 * 
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
@RunWith(Arquillian.class)
@ProbeTestClass(contributors = "laurent.prevost@probedock.io", tickets = "feature-converter", tags = { "converter", "operation" })
public class OperationConverterServiceTest {

	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive jar = ShrinkWrap
			.create(JavaArchive.class)
			.addAsManifestResource("test-persistence.xml", "persistence.xml")
			.addClass(OperationConverterService.class)
			.addClass(OperationConverterServiceImpl.class)
			.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

		return jar;
	}

	@EJB
	private OperationConverterService operationConverterService;

	@Test
	@ProbeTest(tags = "error")
	public void givenAnUnknownOperationTypeTheConversionShouldFail() {
		try {
			operationConverterService.convert(new OperationTO("unk", 1, 2));
			failBecauseExceptionWasNotThrown(CalculationException.class);
		}
		catch (CalculationException ce) {}
	}
	
	@Test
	public void itShouldConvertAddOperation() throws CalculationException {
		assertThat(operationConverterService.convert(new OperationTO("add", 1, 2))).isInstanceOf(OperationAdd.class);
	}

	@Test
	public void itShouldConvertSubOperation() throws CalculationException {
		assertThat(operationConverterService.convert(new OperationTO("sub", 1, 2))).isInstanceOf(OperationSub.class);
	}

	@Test
	public void itShouldConvertMulOperation() throws CalculationException {
		assertThat(operationConverterService.convert(new OperationTO("mul", 1, 2))).isInstanceOf(OperationMul.class);
	}

	@Test
	public void itShouldConvertDivOperation() throws CalculationException {
		assertThat(operationConverterService.convert(new OperationTO("div", 1, 2))).isInstanceOf(OperationDiv.class);
	}
	
	@Test
	public void itShouldConvertComplexOperation() throws CalculationException {
		Operation op = operationConverterService.convert(
			new OperationTO(
				"add", 
				new OperationTO("sub", 1, 3), 
				new OperationTO("mul", 2, 4)
			)
		);
			
		assertThat(op).isInstanceOf(OperationAdd.class);
		assertThat(op.getLeftOperation()).isNotNull().isInstanceOfAny(OperationSub.class);
		assertThat(op.getRightOperation()).isNotNull().isInstanceOfAny(OperationMul.class);
	}
}

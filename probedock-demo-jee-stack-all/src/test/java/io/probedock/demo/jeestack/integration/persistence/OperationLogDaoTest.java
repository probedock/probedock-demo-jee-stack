package io.probedock.demo.jeestack.integration.persistence;

import io.probedock.client.annotations.ProbeTestClass;
import io.probedock.demo.jeestack.model.OperationLog;
import io.probedock.demo.jeestack.persistence.OperationLogDao;
import io.probedock.demo.jeestack.persistence.OperationLogDaoImpl;
import io.probedock.demo.junit.OperationAdd;
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
 * Integration tests for {@link OperationLogDao}
 * 
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
@RunWith(Arquillian.class)
@ProbeTestClass(contributors = "laurent.prevost@probedock.io", tickets = "feature-persistence", tags = { "persistence", "operation" })
public class OperationLogDaoTest {

	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive jar = ShrinkWrap
			.create(JavaArchive.class)
			.addAsManifestResource("test-persistence.xml", "persistence.xml")
			.addClass(OperationLog.class)
			.addClass(OperationLogDao.class)
			.addClass(OperationLogDaoImpl.class)
			.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

		return jar;
	}

	@EJB
	OperationLogDao operationLogDao;
	
	@Test
	public void itShouldBePossibleToPersistAnOperation() {
		OperationLog ol = operationLogDao.create(new OperationAdd(2, 3), 5);
		
		assertThat(ol.getId()).isNotNull();
		assertThat(ol.getOperation()).isEqualTo("( 2 + 3 )");
		assertThat(ol.getResult()).isEqualTo(5);
	}
	
	@Test
	public void itShouldBePossibleToRetrieveAllOperations() {
		List<OperationLog> operationLogs = operationLogDao.findAll();
		
		int beforeSize = operationLogs.size();
		
		operationLogDao.create(new OperationAdd(2, 3), 5);
		operationLogDao.create(new OperationAdd(2, 4), 6);
		
		operationLogs = operationLogDao.findAll();
		
		assertThat(operationLogs).isNotEmpty().hasSize(beforeSize + 2);
	}
}

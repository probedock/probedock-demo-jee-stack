package io.probedock.demo.jeestack.model;

import io.probedock.client.annotations.ProbeTest;
import io.probedock.client.annotations.ProbeTestClass;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit test for the entity {@link OperationLog}
 * 
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
@ProbeTestClass(contributors = "laurent.prevost@probedock.io")
public class OperationLogTest {
	@Test
	@ProbeTest(tags = "constructor", tickets = "feature-model")
	public void itShouldBePossibleToBuildAnOperationLogWithExecutionDateResultAndOperation() {
		Date date = Calendar.getInstance().getTime();
		
		OperationLog ol = new OperationLog(date, "( 1 + 2 )", 3);
		
		assertThat(ol.getId()).isNull();
		assertThat(ol.getOperation()).isEqualTo("( 1 + 2 )");
		assertThat(ol.getResult()).isEqualTo(3);
		assertThat(ol.getExecutionDate()).isEqualTo(date);
	}
}

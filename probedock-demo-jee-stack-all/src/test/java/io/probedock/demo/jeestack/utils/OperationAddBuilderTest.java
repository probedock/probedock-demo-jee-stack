package io.probedock.demo.jeestack.utils;

import io.probedock.client.annotations.ProbeTestClass;
import io.probedock.demo.junit.Operation;
import io.probedock.demo.junit.OperationAdd;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for {@link OperationAddBuilder}
 * 
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
@ProbeTestClass(contributors = "laurent.prevost@probedock.io", tickets = "feature-builder", tags = { "builder", "add" })
public class OperationAddBuilderTest {
	@Test
	public void itShouldBePossibleToBuildOperationFromTwoIntegers() {
		Operation op = new OperationAddBuilder().build(1, 2);
		
		assertThat(op).isInstanceOf(OperationAdd.class);
		assertThat(op.getLeftOperand()).isEqualTo(1);
		assertThat(op.getRightOperand()).isEqualTo(2);
		assertThat(op.getLeftOperation()).isNull();
		assertThat(op.getRightOperation()).isNull();
	}
	
	@Test
	public void itShouldBePossibleToBuildOperationFromTwoOperations() {
		Operation left = new OperationAdd(1, 2);
		Operation right = new OperationAdd(3, 4);
		Operation op = new OperationAddBuilder().build(left, right);
		
		assertThat(op).isInstanceOf(OperationAdd.class);
		assertThat(op.getLeftOperand()).isEqualTo(0);
		assertThat(op.getRightOperand()).isEqualTo(0);
		assertThat(op.getLeftOperation()).isNotNull().isEqualTo(left);
		assertThat(op.getRightOperation()).isNotNull().isEqualTo(right);
	}
	
	@Test
	public void itShouldBePossibleToBuildOperationFromLeftIntegerAndRightOperation() {
		Operation left = new OperationAdd(1, 2);
		Operation op = new OperationAddBuilder().build(left, 3);
		
		assertThat(op).isInstanceOf(OperationAdd.class);
		assertThat(op.getLeftOperand()).isEqualTo(0);
		assertThat(op.getRightOperand()).isEqualTo(3);
		assertThat(op.getLeftOperation()).isNotNull().isEqualTo(left);
		assertThat(op.getRightOperation()).isNull();
	}
	
	@Test
	public void itShouldBePossibleToBuildOperationFromLeftOperationAndRightInteger() {
		Operation right = new OperationAdd(3, 4);
		Operation op = new OperationAddBuilder().build(1, right);
		
		assertThat(op).isInstanceOf(OperationAdd.class);
		assertThat(op.getLeftOperand()).isEqualTo(1);
		assertThat(op.getRightOperand()).isEqualTo(0);
		assertThat(op.getLeftOperation()).isNull();
		assertThat(op.getRightOperation()).isNotNull().isEqualTo(right);
	}
}

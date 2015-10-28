package io.probedock.demo.jeestack.utils;

import io.probedock.client.annotations.ProbeTestClass;
import io.probedock.demo.junit.Operation;
import io.probedock.demo.junit.OperationAdd;
import io.probedock.demo.junit.OperationDiv;
import io.probedock.demo.junit.OperationMul;
import io.probedock.demo.junit.OperationSub;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for {@link OperationBuilder}
 * 
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
@ProbeTestClass(contributors = "laurent.prevost@probedock.io", tickets = "feature-builder", tags = "builder")
public class OperationBuilderTest {
	/**
	 * Operations to help for the tests
	 */
	private final OperationAdd opAdd = new OperationAdd(1, 2);
	private final OperationSub opSub = new OperationSub(1, 2);
	private final OperationMul opMul = new OperationMul(1, 2);
	private final OperationDiv opDiv = new OperationDiv(1, 2);
	
	/**
	 * Implementation of the operation builder to ensure the build method is working properly
	 */
	private class TestOperationBuilder extends OperationBuilder {
		@Override
		protected Operation build(int left, int right) {
			return opAdd;
		}

		@Override
		protected Operation build(int left, Operation right) {
			return opSub;
		}

		@Override
		protected Operation build(Operation left, int right) {
			return opMul;
		}

		@Override
		protected Operation build(Operation left, Operation right) {
			return opDiv;
		}
	}
	
	@Test
	public void itShouldBePossibleToBuildTheCorrectOperationFromLeftIntegerAndRightInteger() {
		Operation op = new TestOperationBuilder().build(1, 2, null, null);
		assertThat(op).isEqualTo(opAdd);
	}

	@Test
	public void itShouldBePossibleToBuildTheCorrectOperationFromLeftIntegerAndRightOperation() {
		Operation op = new TestOperationBuilder().build(1, 2, null, opAdd);
		assertThat(op).isEqualTo(opSub);
	}

	@Test
	public void itShouldBePossibleToBuildTheCorrectOperationFromLeftOperationAndRightInteger() {
		Operation op = new TestOperationBuilder().build(1, 2, opAdd, null);
		assertThat(op).isEqualTo(opMul);
	}

	@Test
	public void itShouldBePossibleToBuildTheCorrectOperationFromLeftOperationAndRightOperation() {
		Operation op = new TestOperationBuilder().build(1, 2, opAdd, opAdd);
		assertThat(op).isEqualTo(opDiv);
	}
}

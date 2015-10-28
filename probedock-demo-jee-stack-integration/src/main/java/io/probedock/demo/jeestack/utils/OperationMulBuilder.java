package io.probedock.demo.jeestack.utils;

import io.probedock.demo.junit.Operation;
import io.probedock.demo.junit.OperationMul;

/**
 * Build new {@link OperationMul} objects
 *
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */

public class OperationMulBuilder extends OperationBuilder {
    @Override
    protected Operation build(int left, int right) {
        return new OperationMul(left, right);
    }

    @Override
    protected Operation build(int left, Operation right) {
        return new OperationMul(left, right);
    }

    @Override
    protected Operation build(Operation left, int right) {
        return new OperationMul(left, right);
    }

    @Override
    protected Operation build(Operation left, Operation right) {
        return new OperationMul(left, right);
    }
}

package io.probedock.demo.jeestack.utils;

import io.probedock.demo.junit.Operation;
import io.probedock.demo.junit.OperationAdd;

/**
 * Build new {@link OperationAdd} objects
 *
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */

public class OperationAddBuilder extends OperationBuilder {
    @Override
    protected Operation build(int left, int right) {
        return new OperationAdd(left, right);
    }

    @Override
    protected Operation build(int left, Operation right) {
        return new OperationAdd(left, right);
    }

    @Override
    protected Operation build(Operation left, int right) {
        return new OperationAdd(left, right);
    }

    @Override
    protected Operation build(Operation left, Operation right) {
        return new OperationAdd(left, right);
    }
}

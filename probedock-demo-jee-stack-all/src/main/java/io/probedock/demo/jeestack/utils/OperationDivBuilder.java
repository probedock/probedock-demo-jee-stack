package io.probedock.demo.jeestack.utils;

import io.probedock.demo.junit.Operation;
import io.probedock.demo.junit.OperationDiv;

/**
 * Build new {@link OperationDiv} objects
 *
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */

public class OperationDivBuilder extends OperationBuilder {
    @Override
    protected Operation build(int left, int right) {
        return new OperationDiv(left, right);
    }

    @Override
    protected Operation build(int left, Operation right) {
        return new OperationDiv(left, right);
    }

    @Override
    protected Operation build(Operation left, int right) {
        return new OperationDiv(left, right);
    }

    @Override
    protected Operation build(Operation left, Operation right) {
        return new OperationDiv(left, right);
    }
}

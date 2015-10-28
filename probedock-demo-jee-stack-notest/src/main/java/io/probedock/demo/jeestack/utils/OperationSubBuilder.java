package io.probedock.demo.jeestack.utils;

import io.probedock.demo.junit.Operation;
import io.probedock.demo.junit.OperationSub;

/**
 * Build new {@link OperationSub} objects
 *
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */

public class OperationSubBuilder extends OperationBuilder {
    @Override
    protected Operation build(int left, int right) {
        return new OperationSub(left, right);
    }

    @Override
    protected Operation build(int left, Operation right) {
        return new OperationSub(left, right);
    }

    @Override
    protected Operation build(Operation left, int right) {
        return new OperationSub(left, right);
    }

    @Override
    protected Operation build(Operation left, Operation right) {
        return new OperationSub(left, right);
    }
}

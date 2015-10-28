package io.probedock.demo.jeestack.utils;

import io.probedock.demo.junit.Operation;

/**
 * Build new {@link Operation} objects
 *
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
public abstract class OperationBuilder {
    /**
     * Build a new operation based on the data given.
     *
     * If leftOperation and/or rightOperation is null, then the left/right integers are used.
     *
     * @param left Left integer
     * @param right Right integer
     * @param leftOperation The left operation. Can be null
     * @param rightOperation The right operation. Can be null
     * @return The new operation created
     */
    public Operation build(int left, int right, Operation leftOperation, Operation rightOperation) {
        if (leftOperation != null && rightOperation != null) {
            return build(leftOperation, rightOperation);
        }
        else if (leftOperation != null) {
            return build(leftOperation, right);
        }
        else if (rightOperation != null) {
            return build(left, rightOperation);
        }
        else {
            return build(left, right);
        }
    }

    /**
     * Build an operation with two integers.
     *
     * @param left Left is an integer
     * @param right Right is an integer
     * @return New operation
     */
    protected abstract Operation build(int left, int right);

    /**
     * Build an operation with left as an integer and right as an operation.
     *
     * @param left Left is an integer
     * @param right Right is an operation
     * @return New operation
     */
    protected abstract Operation build(int left, Operation right);

    /**
     * Build an operation with left as an operation and right as an integer.
     *
     * @param left Left is an operation
     * @param right Right is an integer
     * @return New operation
     */
    protected abstract Operation build(Operation left, int right);

    /**
     * Build an operation with two operations
     *
     * @param left Left is an operation
     * @param right Right is an operation
     * @return New operation
     */
    protected abstract Operation build(Operation left, Operation right);
}

package io.probedock.demo.jeestack.service;

import javax.ejb.ApplicationException;

/**
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
@ApplicationException
public class CalculationException extends Exception {
    public CalculationException(String message) {
        super(message);
    }
}

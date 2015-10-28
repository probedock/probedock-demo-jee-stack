package io.probedock.demo.jeestack.service;

import io.probedock.demo.jeestack.to.OperationTO;
import io.probedock.demo.junit.Operation;

import javax.ejb.Local;

/**
 * Converter service to convert {@link OperationTO} to {@link Operation}.
 *
 * The {@link Operation} class comes from the Demo implementation of Junit Probe Dock client.
 *
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
@Local
public interface OperationConverterService {
    /**
     * Convert a transfer object to real operation implementation
     *
     * @param operation The operation to convert
     * @return The operation converted
		 * @throws CalculationException When the operation is unknown
     */
    Operation convert(OperationTO operation) throws CalculationException ;
}

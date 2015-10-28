package io.probedock.demo.jeestack.service;

import io.probedock.demo.jeestack.persistence.OperationLogDao;
import io.probedock.demo.jeestack.to.OperationTO;
import io.probedock.demo.jeestack.to.ResultTO;
import io.probedock.demo.junit.Operation;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Implementation of {@link CalculatorService}.
 *
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
@Stateless
public class CalculatorServiceImpl implements CalculatorService {

	@EJB
	private OperationConverterService operationConverterService;

	@EJB
	private OperationLogDao operationLogDao;

	@Override
	public ResultTO process(OperationTO operation) throws CalculationException {
		Operation op = operationConverterService.convert(operation);
		
		try {
			int result = op.process();
			operationLogDao.create(op, result);
			return new ResultTO(result);
		}
		catch (IllegalStateException ise) {
			throw new CalculationException(ise.getMessage());
		}
	}
}

package io.probedock.demo.jeestack.utils.finders;

import io.probedock.junitee.finder.IFinder;
import io.probedock.demo.jeestack.persistence.OperationLogDao;
import javax.ejb.EJB;

/**
 * Operation log finder to do some queries
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class OperationLogFinder implements IFinder {
	// This EJB will be injected automatically with a persistence context
	// if required by junitee-data-utils.
	@EJB
	private OperationLogDao operationLogDao;

	public int count() {
		// This code is not production ready, it is not the way to do that.
		// You should think about a JPQL query to get this information.
		// This implementation is only for demo purpose.
		return operationLogDao.findAll().size();
	}
}

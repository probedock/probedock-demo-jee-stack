package io.probedock.demo.jeestack.persistence;

import io.probedock.demo.jeestack.model.OperationLog;
import io.probedock.demo.junit.Operation;
import java.util.List;
import javax.ejb.Local;

/**
 * Manage the {@link OperationLog} entities
 * 
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
@Local
public interface OperationLogDao {
	/**
	 * Create an operation log from an operation
	 * 
	 * @param op The operation to save
	 * @param result The result of the operation. No evaluation is done in the DAO.
	 * @return The operation persisted
	 */
	OperationLog create(Operation op, int result);

	/**
	 * Retrieve the list of operations
	 * 
	 * @return The list of operations
	 */
	List<OperationLog> findAll();
}

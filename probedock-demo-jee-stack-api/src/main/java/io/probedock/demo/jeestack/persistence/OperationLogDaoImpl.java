package io.probedock.demo.jeestack.persistence;

import io.probedock.demo.jeestack.model.OperationLog;
import io.probedock.demo.junit.Operation;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Implementation of {@link OperationLogDao}
 * 
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
@Stateless
public class OperationLogDaoImpl implements OperationLogDao {
	@PersistenceContext
	private EntityManager em;

	@Override
	public OperationLog create(Operation op, int result) {
		OperationLog ol = new OperationLog(Calendar.getInstance().getTime(), op.toString(), result);
		
		em.persist(ol);
		em.flush();
		
		return ol;
	}
	
	@Override
	public List<OperationLog> findAll() {
		return new ArrayList<>(em.createQuery("SELECT ol FROM OperationLog ol").getResultList());
	}
}

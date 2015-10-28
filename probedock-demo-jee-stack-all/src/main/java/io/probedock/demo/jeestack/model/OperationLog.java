package io.probedock.demo.jeestack.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity to store the operations done in the system
 * 
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
@Entity
public class OperationLog implements Serializable {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date executionDate;
	
	private String operationLine;
	
	private int operationResult;

	public OperationLog() {
	}

	public OperationLog(Date executionDate, String operation, int result) {
		this.executionDate = executionDate;
		this.operationLine = operation;
		this.operationResult = result;
	}

	public Long getId() {
		return id;
	}

	public Date getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}

	public String getOperation() {
		return operationLine;
	}

	public void setOperation(String operation) {
		this.operationLine = operation;
	}

	public int getResult() {
		return operationResult;
	}

	public void setResult(int result) {
		this.operationResult = result;
	}
}

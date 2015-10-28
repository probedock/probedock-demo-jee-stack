package io.probedock.demo.jeestack.to;

/**
 * Operation
 *
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
public class OperationTO {
	/**
	 * Type of the operation (add, sub, mul or div)
	 */
	private String type;

	/**
	 * Integer numbers
	 */
	private int left;
	private int right;

	/**
	 * Composed operations
	 */
	private OperationTO leftOperation;
	private OperationTO rightOperation;

	/**
	 * Empty constructor
	 */
	public OperationTO() {}

	/**
	 * Constructor
	 *
	 * @param type The type of operation
	 * @param left Left integer
	 * @param right Right integer
	 */
	public OperationTO(String type, int left, int right) {
		this.type = type;
		this.left = left;
		this.right = right;
	}

	/**
	 * Constructor
	 *
	 * @param type The type of operation
	 * @param left Left integer
	 * @param right Right operation
	 */
	public OperationTO(String type, int left, OperationTO right) {
		this.type = type;
		this.left = left;
		this.rightOperation = right;
	}

	/**
	 * Constructor
	 *
	 * @param type The type of operation
	 * @param left Left operation
	 * @param right Right integer
	 */
	public OperationTO(String type, OperationTO left, int right) {
		this.type = type;
		this.leftOperation = left;
		this.right = right;
	}

	/**
	 * Constructor
	 *
	 * @param type The type of operation
	 * @param left Left operation
	 * @param right Right operation
	 */
	public OperationTO(String type, OperationTO left, OperationTO right) {
		this.type = type;
		this.leftOperation = left;
		this.rightOperation = right;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public OperationTO getLeftOperation() {
		return leftOperation;
	}

	public void setLeftOperation(OperationTO leftOperation) {
		this.leftOperation = leftOperation;
	}

	public OperationTO getRightOperation() {
		return rightOperation;
	}

	public void setRightOperation(OperationTO rightOperation) {
		this.rightOperation = rightOperation;
	}
}

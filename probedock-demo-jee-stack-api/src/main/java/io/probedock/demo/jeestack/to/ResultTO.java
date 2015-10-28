package io.probedock.demo.jeestack.to;

/**
 * Operation result
 *
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
public class ResultTO {
	private int result;

	public ResultTO() {
	}

	public ResultTO(int result) {
		this.result = result;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
}

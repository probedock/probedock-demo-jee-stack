package io.probedock.demo.jeestack.to;

/**
 * Error encountered during calculation.
 *
 * @author Laurent Prevost <laurent.prevost@probedock.io>
 */
public class ErrorTO {
    private String message;

    public ErrorTO() {
    }

    public ErrorTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

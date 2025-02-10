package br.nom.abmpicoli.spring_scon_poc.exception;

/**
 * The Basic Exception dictates the behavior for all code.
 * It will be forbidden to throw any exception that doesn't inherit from
 * Basic Exception.
 */
public class SSPFrameworkException extends RuntimeException {

    /**
     *
     * @param code the message code.
     * @param arguments arguments to provide to the message code, to show details about the message if the message
     *                  is parameterized.
     */
    public SSPFrameworkException(MessageCode code, Object... arguments) {
        super(code.format(arguments));
    }

    public SSPFrameworkException(MessageCode code, Throwable cause, Object... arguments) {
        super(code.format(arguments),cause);
    }

}

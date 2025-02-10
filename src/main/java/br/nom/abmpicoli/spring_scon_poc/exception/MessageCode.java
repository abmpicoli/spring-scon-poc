package br.nom.abmpicoli.spring_scon_poc.exception;

import br.nom.abmpicoli.spring_scon_poc.events.Severity;

/**
 * Represents a unique code within the codebase that identifies a message event: be it logging-based or exception raised.
 */
public interface MessageCode {

    /**
     * Formats the message code.
     * @param args
     * @return
     */
    default String format(Object... args) {
        return getHierarchy()+"-"+getCode()+" "+getFormat().formatted(args);
    }

    /**
     * Gets the message code. Must be unique in the whole codebase.
     * @return the message code
     */
    String getCode();

    /**
     * Gets the format used to format the arguments
     */
    String getFormat();

    /**
     * Gets the parent message code. A class must have its unique message code,
     * and method invocation should inherit it.
     *
     * @return the parent code.
     */
    MessageCode getParent();

    /**
     * Gets the message hierarchy. The suggested implementation is to cycle through the
     * parent hierarchy and return the "path" to this message code.
     * @return a string representation of the full message hierarchy.
     */
    String getHierarchy();

    /**
     * Gets the severity of this code, according to the event system.
     * <br/>
     * A severity specifies the channels where the event must be sent:
     * Example: only logging, some event communication system like dynatrace, etc.
     * @return
     */
    Severity getSeverity();

}

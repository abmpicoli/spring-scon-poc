package br.nom.abmpicoli.spring_scon_poc.events;

/**
 * Represents an event severity:
 */
public enum Severity {
    /**
     * Represents an information that has direct value to the end user,
     * but it is not an abnormal execution.
     * A typical logger would send this as an info message.
     * <br/>
     *
     */
    INFO,
    /**
     * Represents information that the end user may need to help diagnose a
     * configuration issue, but otherwise can be removed from typical logging
     * unless some event of bigger severity is raised.
     */
    DEBUG,
    /**
     * Represents information that the framework developer may need to help
     * diagnose an issue.
     */
    TRACE,
    /**
     * Represents abnormal execution issues. The end user should be notified
     * about this to, say, invoke some proactive recovery procedures:
     * <br/>
     * Examples:
     * <ul><li>An input with malformed data.</li>
     * <li>An http 400 error response mentioning invalid data request that happens with one or other record, but not
     * for all received data</li></ul>
     */
    WARN,
    /**
     * Represent a serious execution issue. Alerts to operators / incident management procedures should be raised,
     * but it may be something that can be solved in a long time, say, 3 weeks.
     *
     */
    SEV4,
    /**
     * Represents a more serious execution issue: Alerts raised.
     * Expected resolution time, 1 week
     */
    SEV3,
    /**
     *
     */
    SEV2,
}

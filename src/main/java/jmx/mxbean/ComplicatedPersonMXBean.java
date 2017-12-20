package jmx.mxbean;

/**
 * {@link jmx.mbean.SimplePerson}包装得到的"复杂"的人类.
 *
 * @author skywalker
 */
public interface ComplicatedPersonMXBean {

    SimplePerson getPerson();

    void setPerson(SimplePerson person);

}

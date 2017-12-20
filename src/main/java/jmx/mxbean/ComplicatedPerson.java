package jmx.mxbean;

/**
 * {@inheritDoc}
 *
 * @author zhao.xudong
 */
public class ComplicatedPerson implements ComplicatedPersonMXBean {

    private SimplePerson simplePerson = new SimplePerson("小红", 20);

    @Override
    public SimplePerson getPerson() {
        synchronized (simplePerson) {
            return simplePerson;
        }
    }

    @Override
    public void setPerson(SimplePerson person) {
        synchronized (simplePerson) {
            this.simplePerson = person;
        }
    }

}

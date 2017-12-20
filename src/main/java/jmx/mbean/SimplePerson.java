package jmx.mbean;

/**
 * {@inheritDoc}.
 *
 * @author skywalker
 */
public class SimplePerson implements SimplePersonMBean {

    private String name = "小明";
    private int age = 30;

    @Override
    public void echo(String str) {
        System.out.println(str);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

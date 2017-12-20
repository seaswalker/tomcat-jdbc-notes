package jmx.mbean;

/**
 * 简单的MBean定义.
 *
 * @author skywalker
 */
public interface SimplePersonMBean {

    String getName();

    int getAge();

    void echo(String str);

    /**
     * setter方法定义在接口中才可以动态修改值.
     */
    void setName(String name);

    void setAge(int age);

}

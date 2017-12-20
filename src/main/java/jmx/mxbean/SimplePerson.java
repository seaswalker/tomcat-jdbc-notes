package jmx.mxbean;

import java.beans.ConstructorProperties;

public class SimplePerson{

    private String name;
    private int age;

    @ConstructorProperties({"name", "age"})
    public SimplePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void echo(String str) {
        System.out.println(str);
    }

    public String getName() {
        return this.name;
    }

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

package model;

public class Member {
    private String name;
    private int age;

    Member(String name, int age) {
        setAge(age);
        setName(name);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}

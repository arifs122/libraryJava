package model;

public class Member {
    private String name;
    private int age;
    String gender;
    public Member(String name, int age, String gender) {
        setAge(age);
        setName(name);
        setGender(gender);
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
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
}

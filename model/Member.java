package model;

public class Member {
    private String name;
    private String age;
    private String gender;
    public Member(String name, String age, String gender) {
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
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
}

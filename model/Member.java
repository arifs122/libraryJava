package model;

public class Member {
    private String name;
    private int age;
    private String gender;
    private boolean canBorrow;

    public Member(String name, int age, String gender, boolean canBorrow) {
        setAge(age);
        setName(name);
        setGender(gender);
        setCanBorrow(canBorrow);
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
    public boolean getCanBorrow() {
        return canBorrow;
    }
    public void setCanBorrow(boolean canBorrow) { this.canBorrow = canBorrow; }
}

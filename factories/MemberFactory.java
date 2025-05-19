package factories;

import model.*;

public class MemberFactory {

    public static Member create(String name, int age, String gender, boolean canBorrow) {

        String lowerGender = gender.toLowerCase();
        if (!lowerGender.equals("male") && !lowerGender.equals("female")) {
            throw new IllegalArgumentException("Unknown gender: " + gender);
        }

        return new Member(name, age, gender, canBorrow);
    }
}

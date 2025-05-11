package factories;

import model.*;

public class MemberFactory {

    public static Member create(String name, int age, String gender, boolean canBorrow){

        return switch (gender.toLowerCase()) {
            case "male", "female" -> new Member(name, age, gender, canBorrow);
            default -> throw new IllegalArgumentException("Unknown gender: " + gender);
        };


    }

}

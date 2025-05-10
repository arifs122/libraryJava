package factories;

import model.*;

public class MemberFactory {

    public static Member create(String name, int age, String gender, boolean canBorrow){

        switch (gender.toLowerCase()) {

            case "male":
                return new Member(name, age, gender, canBorrow);

            case "female":
                return new Member(name, age, gender, canBorrow);

            default:
                throw new IllegalArgumentException("Unknown gender: " + gender);

        }


    }

}

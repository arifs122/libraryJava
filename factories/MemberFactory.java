package factories;

import model.*;

public class MemberFactory {

    public static Member create(String name, String age, String gender){

        switch (gender.toLowerCase()) {

            case "male":
                return new Member(name, age, gender);

            case "female":
                return new Member(name, age, gender);

            default:
                throw new IllegalArgumentException("Unknown gender: " + gender);

        }


    }

}

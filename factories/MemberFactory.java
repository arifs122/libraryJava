package factories;

import model.*;

/*Factory design pattern kullanılarak üyeler oluşuturuluyor
* */
public class MemberFactory {

    public static Member create(String name, int age, String gender, boolean canBorrow){

        return switch (gender.toLowerCase()) {
            case "male", "female" -> new Member(name, age, gender, canBorrow);
            default -> throw new IllegalArgumentException("Unknown gender: " + gender);
        };


    }

}

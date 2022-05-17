////////////////////////////////////////////////////////////////////
// Alessandro Cavaliere 1224440
// Filippo Brugnolaro 1217321
////////////////////////////////////////////////////////////////////
package it.unipd.mtss;

import java.time.LocalDate;

public class User {
    private String cf, name, surname, email;
    private LocalDate birthdate;

    User(String cf, String name, String surname,
         String email, LocalDate birth) {
        this.cf = cf;
        this.name = name;
        this.surname = surname;
        this.birthdate = birth;
        this.email = email;
    }

    public boolean isOver18() {
        return LocalDate.now().minusYears(18).isAfter(birthdate);
    }
}

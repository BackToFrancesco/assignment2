package it.unipd.mtss;

import java.util.Date;

public class User {
    private String cf, name, surname, email;
    private Date birthdate;

    User(String cf, String name, String surname, String email, Date birth) {
        this.cf = cf;
        this.name = name;
        this.surname = surname;
        this.birthdate = birth;
        this.email = email;
    }
}

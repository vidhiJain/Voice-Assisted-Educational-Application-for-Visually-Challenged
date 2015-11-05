package org.arise.enums;

/**
 * Created by Arpit Phanda on 3/11/2015.
 */
public enum SharedPreferenceEnum {
    USERNAME("username"),
    PASSWORD("password"),
    FIRST_NAME("fname"),
    LAST_NAME("lname"),
    CONTACT("contact"),
    COUNTRY("country"),
    QUALIFICATION("qualification"),
    GENDER("gender"),
    DOB("dob"),
    EMAIL("email");

    private String key;
    SharedPreferenceEnum(String keyValue) {
        key = keyValue;
    }

    @Override
    public String toString() {
        return this.key;
    }
}

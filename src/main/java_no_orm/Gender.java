package main.java_no_orm;

enum Gender {
    MALE((byte)0),
    FEMALE((byte)1);

    byte Gender = 0;
    Gender(byte Gender) {
        this.Gender = Gender;
    }
}

package main.java_no_orm;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

public class Employee {
    private int id;

    private String fullName;

    private Date birthDate;

    private byte gender;

    public Employee(String FIO, Date date, byte gender) {
        this.birthDate = date;
        this.fullName = FIO;
        this.gender = gender;


    }
    public Employee() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte Gender) {
        this.gender = Gender;
    }


    public int getAge(){
        return Period.between(this.birthDate.toLocalDate(), LocalDate.now()).getYears();
    }

    static String alphaNumeric = "abcdefghijklmnopqrstuvwxyz";
    public static Employee generateEmployee() {


        String randomFIO = randomFIO();
        Date randomDate = randomDate();

        Random random = new Random();


        return new Employee(randomFIO,randomDate,(byte)random.nextInt(2));
    }

    public static Employee generateMaleEmployeeFIOStartingWithF() {
        String randomFIO = "F" + randomFIO();
        Date randomDate = randomDate();



        return new Employee(randomFIO,randomDate, Gender.MALE.Gender);
    }
    private static Date randomDate() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(random.nextInt(100)+1970);
        sb.append("-");
        sb.append(random.nextInt(11)+1);
        sb.append("-");
        sb.append(random.nextInt(29)+1);

        return Date.valueOf(sb.toString());
    }

    public static String randomFIO(){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int repeats = 3; repeats > 0; repeats--) {
            int length = random.nextInt(8) + 4;
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(alphaNumeric.length());
                char randomChar = alphaNumeric.charAt(index);
                if(i == 0) {
                    randomChar = Character.toUpperCase(randomChar);
                }
                sb.append(randomChar);
            }

            sb.append(" ");
        }
        return sb.toString();
    }


    public void addToDb(Connection connection) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO employees (full_name, birth_date, gender) VALUES (?, ?, ?)");
            stmt.setString(1, this.getFullName());
            stmt.setDate(2, this.getBirthDate());
            stmt.setByte(3, this.getGender());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Employee " +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", birthDate=" + birthDate + "(age " + this.getAge() +")" +
                ", Gender=" + gender;
    }
}
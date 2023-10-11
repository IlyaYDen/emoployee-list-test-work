package main.java;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "gender")
    private byte gender;

    public Employee(String FIO, Date date, byte gender) {
        this.birthDate = date;
        this.fullName = FIO;
        this.gender = gender;


    }
    public Employee() {

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


    public void addToDb(SessionFactory sessionFactory) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(this);
            session.getTransaction().commit();
        }
    }

    public int getAge(){
        return Period.between(this.birthDate.toLocalDate(), LocalDate.now()).getYears();
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
package main.java;

import main.java.usecases.TableUseCase;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Date;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No arguments provided");
            return;
        }


        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(main.java.Employee.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        switch (args[0]) {
            case "1":
                TableUseCase.createTable(sessionFactory);
                break;
            case "2":
                Employee e = new Employee(args[1], Date.valueOf(args[2]), Gender.valueOf(args[3].toUpperCase()).Gender);
                e.addToDb(sessionFactory);
                break;
            case "3":

                List<Employee> all = TableUseCase.getAll(sessionFactory);
                printEmployees(all);
                break;
            case "4":
                TableUseCase.generateAndSaveEmployees(sessionFactory);
                break;
            case "5":
                List<Employee> list = TableUseCase.getEmployeesByCriteria(sessionFactory);
                printEmployees(list);
                //for(int a =0; a < 1000; a++) {
                //    Long i = System.currentTimeMillis();
                //    List<Employee> list = TableUseCase.getEmployeesByCriteria(sessionFactory);
                //    Long i2 = System.currentTimeMillis();
                //    System.out.println(i2 - i);
                //}
                //58 мс при не индексированном массиве.
                //55 мс в индекстрованном массиве (CREATE INDEX employees_last_name_index ON employees (gender,full_name))
                break;
            default:
                System.out.println("Invalid argument");
        }
    }

    static void printEmployees(List<Employee> employees) {
        employees.forEach(System.out::println);
    }

}
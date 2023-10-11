package main.java_no_orm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static main.java_no_orm.TableUseCases.*;
import static main.java_no_orm.TableUseCases.printEmployees;

public class Main {
//2 "test test test" 2020-11-11 Male
    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5433/employee_db", "postgres", "admin");

        switch(args[0]) {

            case "1":
                createTable(connection);
                break;

            case "2":
                Employee employee = new Employee(args[1], Date.valueOf(args[2]), Gender.valueOf(args[3].toUpperCase()).Gender);
                employee.addToDb(connection);
                break;

            case "3":
                List<Employee> employees = getAllEmployees(connection);
                printEmployees(employees);
                break;

            case "4":
                generateAndSaveEmployees(connection);
                break;

            case "5":
                List<Employee> maleEmployeesStartingWithF = getEmployeesByCriteria(connection);
                printEmployees(maleEmployeesStartingWithF);
                //for(int i = 0; i < 1000 ; i ++) {
                //    Long i1 = System.currentTimeMillis();
                //    List<Employee> maleEmployeesStartingWithF = getEmployeesByCriteria(connection);
                //    Long i2 = System.currentTimeMillis();
                //    System.out.println(i2 - i1);
                //}
                //56 мс при не индексированном массиве.
                //54 мс в индекстрованном массиве (CREATE INDEX employees_last_name_index ON employees (gender,full_name))
                break;
            default:
                System.out.println("Invalid argument");
        }

        connection.close();
    }


}

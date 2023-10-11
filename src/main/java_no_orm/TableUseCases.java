package main.java_no_orm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableUseCases {

    static void createTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE employees (id SERIAL PRIMARY KEY, full_name VARCHAR(255), birth_date DATE, gender SMALLINT)");
        statement.close();
    }



    static List<Employee> getAllEmployees(Connection connection) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");
        statement.close();
        while(resultSet.next()) {
            Employee employee = new Employee();
            employee.setId(resultSet.getInt("id"));
            employee.setFullName(resultSet.getString("full_name"));
            employee.setBirthDate(resultSet.getDate("birth_date"));
            employee.setGender(resultSet.getByte("gender"));
            employees.add(employee);
        }
        return employees;
    }


    public static void generateAndSaveEmployees(Connection connection) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO employees (full_name, birth_date, gender) VALUES (?, ?, ?)");
        long t1 = System.currentTimeMillis();
            // Generate 1,000,000 employees with roughly even distribution of genders and starting letters
        for (int i = 0; i < 100000; i++) {
            Employee employee = Employee.generateEmployee();
            stmt.setString(1, employee.getFullName());
            stmt.setDate(2, employee.getBirthDate());
            stmt.setByte(3, employee.getGender());
            stmt.addBatch();
            //stmt.execute(); (1)
            stmt.clearParameters();
            if (i % 1000 == 0) {
                Employee employee2 = Employee.generateMaleEmployeeFIOStartingWithF();

                stmt.setString(1, employee2.getFullName());
                stmt.setDate(2, employee2.getBirthDate());
                stmt.setByte(3, employee2.getGender());
                stmt.addBatch();
                stmt.executeBatch();
                //stmt.execute(); (1) Добавление методом bulk insert заняло 2346 мс, В то время как по одному - 14991 мс.
                stmt.clearParameters();
            }
        }
        System.out.println(System.currentTimeMillis()-t1);
        stmt.close();
    }

    static List<Employee> getEmployeesByCriteria(Connection connection) throws SQLException {

        List<Employee> employees = new ArrayList<>();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM employees WHERE gender = 0 AND full_name LIKE 'F%'");
        while(resultSet.next()) {
            Employee employee = new Employee();
            employee.setId(resultSet.getInt("id"));
            employee.setFullName(resultSet.getString("full_name"));
            employee.setBirthDate(resultSet.getDate("birth_date"));
            employee.setGender(resultSet.getByte("gender"));
            employees.add(employee);
        }
        statement.close();
        return employees;
    }

    static void printEmployees(List<Employee> employees) {
        employees.forEach(System.out::println);
    }
}

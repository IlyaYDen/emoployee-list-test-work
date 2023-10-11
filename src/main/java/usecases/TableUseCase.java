package main.java.usecases;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import main.java.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TableUseCase {

    public static void createTable(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            // Hibernate автоматически создаст таблицу на основе класса Employee
            session.getTransaction().commit();
        }
    }

    public static List<Employee> getAll(SessionFactory sessionFactory) {
        List<Employee> employees = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Hibernate automatically creates the table based on the Employee class
            // Create a Criteria object
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
            Root<Employee> root = query.from(Employee.class);
            query.select(root);
            Query<Employee> q = session.createQuery(query);
            employees = q.getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }


    public static void generateAndSaveEmployees(SessionFactory sessionFactory) {

        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            // Generate 1,000,000 employees with roughly even distribution of genders and starting letters
            for (int i = 0; i < 100000; i++) {
                Employee employee = Employee.generateEmployee();
                session.persist(employee);
                if (i % 1000 == 0) {
                    session.flush();
                    System.out.println(i);
                    Employee employee2 = Employee.generateMaleEmployeeFIOStartingWithF();
                    session.persist(employee2);
                }
            }
            System.out.println("end1");

            System.out.println("end2");
            session.getTransaction().commit();
        }
    }


    public static List<Employee> getEmployeesByCriteria(SessionFactory sessionFactory) {
        List<Employee> employees = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Employee> cr = cb.createQuery(Employee.class);
            Root<Employee> root = cr.from(Employee.class);

            cr.select(root).where(
                    cb.and(
                            cb.equal(root.get("Gender"), 0),
                            cb.like(root.get("fullName"), "F%")
                    )
            );

            employees = session.createQuery(cr).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;

    }
}

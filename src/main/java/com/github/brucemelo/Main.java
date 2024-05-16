package com.github.brucemelo;

import com.github.brucemelo.infrastructure.AppHibernate;
import com.github.brucemelo.model.Student;

import static com.github.brucemelo.infrastructure.AppJPAStreamProvider.streamAll;

public class Main {

    public static void main(String[] args) {
        AppHibernate.getSessionFactory().inTransaction(session -> {
            var student = Student.newStudent("Bruce Melo", "bruce@example.com");
            session.persist(student);
        });

        AppHibernate.getSessionFactory().inStatelessTransaction(session -> {
            var student = Student.newStudent("Cássia", "cassia@example.com");
            session.insert(student);
        });

        AppHibernate.getSessionFactory().inStatelessTransaction(session -> {
            var student = Student.newStudent("Mary", "mary@example.com");
            session.insert(student);
        });

        AppHibernate.getSessionFactory().inStatelessTransaction(session -> {
            var student = Student.newStudent("Aerith", "aerith@example.com");
            session.insert(student);
        });

        AppHibernate.getSessionFactory().inSession(session -> {
            var students = session.createSelectionQuery("from Student", Student.class).list();
            students.forEach(student -> {
                System.out.println(student.getName());
            });
        });

        AppHibernate.getSessionFactory().inStatelessSession(statelessSession -> {
            var stream = streamAll(statelessSession, Student.class);
            stream.forEach(student -> System.out.println(student.getName()));
        });

        AppHibernate.getSessionFactory().inStatelessSession(statelessSession -> {
            var stream = streamAll(statelessSession, Student.class);
            stream.where(student -> student.getEmail().equals("mary@example.com"))
                    .forEach(student -> System.out.println(student.getName()));
        });

        AppHibernate.getSessionFactory().inStatelessSession(statelessSession -> {
            var stream = streamAll(statelessSession, Student.class);
            stream.filter(student -> student.getName().contains("C"))
                    .findFirst().ifPresent(student -> System.out.println(student.getName()));
        });

    }

}
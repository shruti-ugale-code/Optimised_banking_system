package com.bank.util;

import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.Scanner;

public class UserService {
    private final Session session;
    private final Scanner scanner;

    public UserService(Session session, Scanner scanner) {
        this.session = session;
        this.scanner = scanner;
    }

    // Registration method
    public void register() {
        System.out.print("Full name: ");
        String fullName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (userExists(email)) {
            System.out.println("User already exists with this email!");
            return;
        }

        User user = new User(fullName, email, password);
        session.save(user);
        System.out.println("✅ Registration successful!");
    }


    public String login() {
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        Query<User> query = session.createQuery(
                "FROM User WHERE email = :email AND password = :password", User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);

        User user = query.uniqueResult();
        return (user != null) ? email : null;
    }


    public boolean userExists(String email) {
        Query<User> query = session.createQuery(
                "FROM User WHERE email = :email", User.class);
        query.setParameter("email", email);
        return query.uniqueResult() != null;
    }
}

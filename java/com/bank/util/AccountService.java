package com.bank.util;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Scanner;

public class AccountService {
    private final Session session;
    private final Scanner scanner;

    public AccountService(Session session, Scanner scanner) {
        this.session = session;
        this.scanner = scanner;
    }

    public boolean accountExists(String email) {
        String hql = "FROM Account WHERE user.email = :email";
        Query<Account> query = session.createQuery(hql, Account.class);
        query.setParameter("email", email);
        return query.uniqueResult() != null;
    }

    public long createAccount(String email) {
        scanner.nextLine();
        System.out.println("Enter initial deposit:");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Set your 4-digit security PIN:");
        String pin = scanner.nextLine();

        // Get the user object from the email
        User user = getUserByEmail(email);
        if (user == null) {
            System.out.println("User not found.");
            return 0;
        }

        Account account = new Account();
        account.setUser(user);
        account.setBalance(balance);
        account.setSecurityPin(pin);

        session.save(account);
        return account.getAccountNo();  // Assuming `@GeneratedValue` on accountNo
    }

    public long getAccountNo(String email) {
        String hql = "FROM Account WHERE user.email = :email";
        Query<Account> query = session.createQuery(hql, Account.class);
        query.setParameter("email", email);
        Account account = query.uniqueResult();

        return account != null ? account.getAccountNo() : 0;
    }

    private User getUserByEmail(String email) {
        String hql = "FROM User WHERE email = :email";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("email", email);
        return query.uniqueResult();
    }
}


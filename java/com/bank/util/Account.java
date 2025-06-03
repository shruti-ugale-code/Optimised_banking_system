package com.bank.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.*;
import java.util.Scanner;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_no")
    private long accountNo;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "balance")
    private double balance;

    @Column(name = "security_pin")
    private String securityPin;

    public Account() {}

    public Account(String userName, String email, double balance, String securityPin) {
        this.userName = userName;
        this.email = email;
        this.balance = balance;
        this.securityPin = securityPin;
    }

    // Getters and setters
    public long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    @ManyToOne
    @JoinColumn(name = "user_id") // this should match the foreign key column name
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getSecurityPin() {
        return securityPin;
    }

    public void setSecurityPin(String securityPin) {
        this.securityPin = securityPin;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter full name:");
        String name = scanner.nextLine();

        System.out.println("Enter email:");
        String email = scanner.nextLine();

        System.out.println("Enter initial amount:");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter security pin:");
        String pin = scanner.nextLine();

        Account account = new Account(name, email, balance, pin);

        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        config.addAnnotatedClass(Account.class);

        SessionFactory factory = config.buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        // Check if account with email exists
        Account existing = (Account) session
                .createQuery("FROM Account WHERE email = :email")
                .setParameter("email", email)
                .uniqueResult();

        if (existing != null) {
            System.out.println("❌ Account already exists with email: " + email);
        } else {
            session.save(account);
            tx.commit();
            System.out.println("✅ Account created. Account No: " + account.getAccountNo());
        }

        session.close();
        factory.close();
    }
}

package com.bank.util;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Scanner;

public class AccountManager {
    private Session session;
    private Scanner scanner;

    public AccountManager(Session session, Scanner scanner) {
        this.session = session;
        this.scanner = scanner;
    }

    public void debitMoney(Long accountNo) {
        scanner.nextLine();
        System.out.print("Enter amount to debit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter security pin: ");
        String pin = scanner.nextLine();

        Transaction tx = session.beginTransaction();

        try {
            Account account = session.get(Account.class, accountNo);
            if (account != null && account.getSecurityPin().equals(pin)) {
                if (account.getBalance() >= amount) {
                    account.setBalance(account.getBalance() - amount);
                    session.update(account);
                    tx.commit();
                    System.out.println("Amount Rs: " + amount + " debited successfully.");
                } else {
                    System.out.println("Insufficient balance.");
                    tx.rollback();
                }
            } else {
                System.out.println("Incorrect account number or pin.");
                tx.rollback();
            }
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }

    public void creditMoney(Long accountNo) {
        scanner.nextLine();
        System.out.print("Enter amount to credit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter security pin: ");
        String pin = scanner.nextLine();

        Transaction tx = session.beginTransaction();

        try {
            Account account = session.get(Account.class, accountNo);
            if (account != null && account.getSecurityPin().equals(pin)) {
                account.setBalance(account.getBalance() + amount);
                session.update(account);
                tx.commit();
                System.out.println("Amount Rs: " + amount + " credited successfully.");
            } else {
                System.out.println("Incorrect account number or pin.");
                tx.rollback();
            }
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }

    public void getBalance(Long accountNo) {
        scanner.nextLine();
        System.out.print("Enter security pin: ");
        String pin = scanner.nextLine();

        Account account = session.get(Account.class, accountNo);
        if (account != null && account.getSecurityPin().equals(pin)) {
            System.out.println("Your account balance is Rs: " + account.getBalance());
        } else {
            System.out.println("Invalid account number or pin.");
        }
    }

    public void transferMoney(Long senderAccountNo) {
        scanner.nextLine();
        System.out.print("Enter receiver account no: ");
        Long receiverAccountNo = scanner.nextLong();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter your security pin: ");
        String pin = scanner.nextLine();

        Transaction tx = session.beginTransaction();

        try {
            Account sender = session.get(Account.class, senderAccountNo);
            Account receiver = session.get(Account.class, receiverAccountNo);

            if (sender != null && receiver != null && sender.getSecurityPin().equals(pin)) {
                if (sender.getBalance() >= amount) {
                    sender.setBalance(sender.getBalance() - amount);
                    receiver.setBalance(receiver.getBalance() + amount);

                    session.update(sender);
                    session.update(receiver);

                    tx.commit();
                    System.out.println("Amount Rs: " + amount + " transferred successfully.");
                } else {
                    System.out.println("Insufficient funds.");
                    tx.rollback();
                }
            } else {
                System.out.println("Invalid account or pin.");
                tx.rollback();
            }
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }
}


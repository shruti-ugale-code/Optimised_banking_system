package com.bank.util;

import com.bank.util.Account;
import com.bank.util.User;
import com.bank.util.AccountManager;
import com.bank.util.AccountService;
import com.bank.util.UserService;
import com.bank.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\u001B[1m\u001B[97mWELCOME TO BANKING SYSTEM!!!!\u001B[0m\n");
            System.out.println("1. REGISTER");
            System.out.println("2. LOGIN");
            System.out.println("3. EXIT");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Transaction tx = session.beginTransaction();

                UserService userService = new UserService(session, scanner);
                AccountService accountService = new AccountService(session, scanner);
                AccountManager accountManager = new AccountManager(session, scanner);

                switch (choice) {
                    case 1:
                        userService.register();
                        break;

                    case 2:
                        String email = userService.login();
                        if (email != null) {
                            System.out.println("User logged in successfully.");

                           if (!accountService.accountExists(email)) {
                                System.out.println("1. OPEN BANK ACCOUNT");
                                System.out.println("2. EXIT");
                                if (scanner.nextInt() == 1) {
                                    long accountNo = accountService.createAccount(email);
                                    System.out.println("ACCOUNT CREATED SUCCESSFULLY");
                                    System.out.println("YOUR ACCOUNT NO IS: " + accountNo);
                                } else {
                                    break;
                                }
                            }

                            long accountNo = accountService.getAccountNo(email);
                            int innerChoice;
                            do {
                                System.out.println("1. Debit money");
                                System.out.println("2. Credit money");
                                System.out.println("3. Transfer money");
                                System.out.println("4. Check balance");
                                System.out.println("5. Log out");
                                System.out.print("Enter your choice: ");
                                innerChoice = scanner.nextInt();

                                switch (innerChoice) {
                                    case 1:
                                        accountManager.debitMoney(accountNo);
                                        break;
                                    case 2:
                                        accountManager.creditMoney(accountNo);
                                        break;
                                    case 3:
                                        accountManager.transferMoney(accountNo);
                                        break;
                                    case 4:
                                        accountManager.getBalance(accountNo);
                                        break;
                                    case 5:
                                        break;
                                    default:
                                        System.out.println("Enter a valid choice");
                                }
                            } while (innerChoice != 5);
                        } else {
                            System.out.println("Incorrect email or password.");
                        }
                        break;

                    case 3:
                        System.out.println("THANK YOU FOR USING BANKING SYSTEM!");
                        System.out.println("EXITING SYSTEM...");
                        tx.commit();
                        return;

                    default:
                        System.out.println("Enter a valid choice.");
                }

                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


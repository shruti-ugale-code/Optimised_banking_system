package com.bank.util;
import com.bank.util.User;
import com.bank.util.Account;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

            // ✅ Register all @Entity classes here
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Account.class); // If you have Account.java as @Entity

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

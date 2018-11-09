package upgrade.wallet.repository;

import upgrade.wallet.model.Transaction;
import upgrade.wallet.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserMockedData {

    private List<User> users;

    private static UserMockedData instance = null;

    public static UserMockedData getInstance() {
        if (instance == null) {
            instance = new UserMockedData();
        }
        return instance;
    }

    public UserMockedData() {
        users = new ArrayList<>();

        users.add(new User("email1", "fname1", "lname1", "pass1", new HashSet<>(), 10.0));
        users.add(new User("email2", "fname2", "lname2", "pass2", new HashSet<>(), 2000.8));
        users.add(new User("email3", "fname3", "lname3", "pass3", new HashSet<>(), 5990.32));
        users.add(new User("email4", "fname4", "lname4", "pass4", new HashSet<>(), 45.90));
        users.add(new User("email5", "fname5", "lname5", "pass5", new HashSet<>(), 23456.9));
        users.add(new User("email6", "fname6", "lname6", "pass6", new HashSet<>(), 9900.45));
        users.add(new User("email7", "fname7", "lname7", "pass7", new HashSet<>(), 83322.33));
        users.add(new User("email8", "fname8", "lname8", "pass8", new HashSet<>(), 998776622.0));

    }

    public User getUserByEmail(String email) {
        for (User u : users) {
            if (u.getEmail().toLowerCase().equals(email)) {
                return u;
            }
        }
        return null;
    }

    public Set<Transaction> getNUserTransactions(String email, int N) {
        Set<Transaction> transactions = new HashSet<>();
        for (User u : users) {
            if (u.getEmail().toLowerCase().equals(email)) {
                for (Transaction t : u.getTransactions()) {
                    if (transactions.size() > N) {
                        break;
                    }
                    transactions.add(t);
                }
                return transactions;
            }
        }
        return null;
    }

    public Set<Transaction> getUserTransactions(String email) {
        for (User u : users) {
            if (u.getEmail().toLowerCase().equals(email)) {
                return u.getTransactions();
            }
        }
        return null;
    }

    public User createUser(String fname, String lname, String email, String pass, double balance) {
        User u = new User(fname, lname, email, pass, new HashSet<>(), balance);
        users.add(u);
        return u;
    }

    public User updateUser(String fname, String lname, String email, String pass) {
        for (User u : users) {
            if (u.getEmail().toLowerCase().equals(email)) {
                int userIndex = users.indexOf(u);
                u.setPassword(pass);
                u.setFirstname(fname);
                u.setLastname(lname);
                users.set(userIndex, u);
                return u;
            }
        }
        return null;
    }

    public Transaction withdrawTransaction(String senderEmail, double amount) {


        for (User u : users) {
            if (u.getEmail().toLowerCase().equals(senderEmail)) {
                Transaction transaction = new Transaction("withdraw", amount, u);
                int userIndex = users.indexOf(u);
                if (u.getBalance() > 0) {
                    u.setBalance(u.getBalance() - amount);
                    users.set(userIndex, u);
                    u.addTransactions(transaction);
                    return transaction;
                } else break;
            }
        }
        return null;
    }

    public Transaction depositTransaction(String senderEmail, double amount) {
        for (User u : users) {
            if (u.getEmail().toLowerCase().equals(senderEmail)) {
                Transaction transaction = new Transaction("withdraw", amount, u);
                int userIndex = users.indexOf(u);
                u.setBalance(u.getBalance() + amount);
                u.addTransactions(transaction);
                users.set(userIndex, u);
                return transaction;
            }
        }
        return null;
    }
}

package upgrade.wallet;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(name = "email")
    private String email;

    @Column(name = "balance")
    private double balance;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<Transaction> transactions;

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransactions(Transaction transactions) {
        this.transactions.add(transactions);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User(String email, String firstname, String lastname, String password, Set<Transaction> transactions, double balance) {
        this.setEmail(email);
        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setPassword(password);
        this.setActive(true);
        this.setTransactions(transactions);
        this.setBalance(balance);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("firstName", firstname)
                .append("lastName", lastname)
                .append("active", active)
                .append("balance", balance)
                .toString();
    }
    public User() {
    }

}
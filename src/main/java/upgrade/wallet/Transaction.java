package upgrade.wallet;


import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "type")
    private String type;

    @Column(name = "amount")
    private double amount;

    @Column(name = "created_on")
    @Temporal(value = TemporalType.DATE)
    private Date timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public Transaction() {
    }

    public Transaction(String type, double amount, User user) {
        this.setType(type);
        this.setAmount(amount);
        this.setTimestamp();
        this.setUser(user);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp() {
        this.timestamp = new Date();
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("amount", amount)
                .append("timestamp", timestamp)
                .toString();
    }
}

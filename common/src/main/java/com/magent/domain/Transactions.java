package com.magent.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created  on 25.05.2016.
 */
@Entity
@Table(name = "ma_transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "account_number", nullable = false)
    private Long account_number;

    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;

    @Column(name = "is_increment_operation", nullable = false)
    private boolean isIncrement;

    @Column(name = "transactioon_summ", nullable = false)
    private BigDecimal summ;

    public Transactions() {
    }

    public Transactions(Long account_number, Date transactionDate, boolean isIncrement, BigDecimal summ) {
        this.account_number = account_number;
        this.transactionDate = transactionDate;
        this.isIncrement = isIncrement;
        this.summ = summ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccount_number() {
        return account_number;
    }

    public void setAccount_number(Long account_id) {
        this.account_number = account_id;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public boolean isIncrement() {
        return isIncrement;
    }

    public void setIncrement(boolean increment) {
        isIncrement = increment;
    }

    public BigDecimal getSumm() {
        return summ;
    }

    public void setSumm(BigDecimal summ) {
        this.summ = summ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transactions that = (Transactions) o;

        if (!account_number.equals(that.account_number)) return false;
        return transactionDate.equals(that.transactionDate);

    }

    @Override
    public int hashCode() {
        int result = account_number.hashCode();
        result = 31 * result + transactionDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "id=" + id +
                ", account_id=" + account_number +
                ", transactionDate=" + transactionDate +
                ", isIncrement=" + isIncrement +
                ", summ=" + summ +
                '}';
    }
}

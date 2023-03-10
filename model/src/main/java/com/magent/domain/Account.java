package com.magent.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created  on 25.05.2016.
 */
@Entity
@Table(name = "ma_accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "acount_number")
    private Long accountNumber;

    @Column(name = "account_balance")
    private Double accountBalance;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "account_number",referencedColumnName = "acount_number",insertable = false,updatable = false)
    private List<Transactions> transactionsList;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "usr_pk",insertable = false,updatable = false)
    private User user;

    public Account() {
    }

    public Account(Long accountNumber, Double accountBalance, Long userId) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.userId = userId;
    }

    public Account(User user) {
        this.accountBalance=0.00;
        this.userId= user.getId();
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Transactions> getTransactionsList() {
        return transactionsList;
    }

}

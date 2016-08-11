package com.magent.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;

/**
 * Created  on 14.07.2016.
 */
@Entity
@Table(name = "ma_user_personal")
public class UserPersonal {

    @Id
    @Column(name = "user_pers_pk",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "ma_usr_id",nullable = false,unique = true)
    private Long userId;

    @Column(name = "usr_pers_pwd",nullable = false)
    private String password;

    @Column(name = "usr_pers_wrong_enters",nullable = false,columnDefinition = "INTEGER DEFAULT 0")
    private int wrongEntersEntering;

    @Column(name = "usr_pers_is_blocked",nullable = false,columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isBlocked;

    @Column(name = "usr_pers_att_counter",nullable = false,columnDefinition = "INTEGER DEFAULT 0")
    private int attemptCounter;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "usr_pers_block_expires")
    private Date blockExpired;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "usr_pers_for_pwd_expire")
    private Date forgotPwdExpireAttempt;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "ma_usr_id",referencedColumnName = "usr_pk",insertable = false,updatable = false)
    private User user;

    public UserPersonal() {
    }

    public UserPersonal(Long userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public int getWrongEntersEntering() {
        return wrongEntersEntering;
    }

    public void setWrongEntersEntering(int wrongEntersEntering) {
        this.wrongEntersEntering = wrongEntersEntering;
    }

    public Date getBlockExpired() {
        return blockExpired;
    }

    public void setBlockExpired(Date blockExpired) {
        this.blockExpired = blockExpired;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public int getAttemptCounter() {
        return attemptCounter;
    }

    public void setAttemptCounter(int attemptCounter) {
        this.attemptCounter = attemptCounter;
    }

    public Date getForgotPwdExpireAttempt() {
        return forgotPwdExpireAttempt;
    }

    public void setForgotPwdExpireAttempt(Date forgotPwdExpireAttempt) {
        this.forgotPwdExpireAttempt = forgotPwdExpireAttempt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPersonal that = (UserPersonal) o;

        if (!id.equals(that.id)) return false;
        return userId.equals(that.userId);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + userId.hashCode();
        return result;
    }
}

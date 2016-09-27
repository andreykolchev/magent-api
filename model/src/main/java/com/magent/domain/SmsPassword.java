package com.magent.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.magent.domain.interfaces.Identifiable;

import javax.persistence.*;
import java.util.Date;

/**
 * Created on 13.06.2016.
 */
@Entity
@Table(name = "ma_sms_pass")
public class SmsPassword implements Identifiable<Long> {

    @Id
    @Column(name = "sms_pwd_pk")
    private Long smsId;

    @Column(name = "sms_user_id",unique = true,nullable = false)
    private Long userId;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "sms_user_id",referencedColumnName = "usr_pk",insertable = false,updatable = false)
    private User user;

    @JsonIgnore
    @Column(name = "sms_password")
    private String smsPass;

    //changed after end become start
    @Column(name = "endPeriod", nullable = false)
    private Date startPeriod;

    public SmsPassword() {
    }

    public SmsPassword(Long smsId, Long userId, String smsPass, Date startPeriod) {
        this.smsId = smsId;
        this.userId = userId;
        this.smsPass = smsPass;
        this.startPeriod = startPeriod;

    }

    public Long getId() {
        return smsId;
    }

    public void setId(Long id) {
        this.smsId = id;
    }

    public String getSmsPass() {
        return smsPass;
    }

    public void setSmsPass(String smsPass) {
        this.smsPass = smsPass;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(Date endPeriod) {
        this.startPeriod = endPeriod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsPassword that = (SmsPassword) o;

        if (!smsId.equals(that.smsId)) return false;
        return userId.equals(that.userId);

    }

    @Override
    public int hashCode() {
        int result = smsId.hashCode();
        result = 31 * result + userId.hashCode();
        return result;
    }
}

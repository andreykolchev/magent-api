package com.magent.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * Created  on 13.06.2016.
 */
@Entity
@Table(name = "ma_sms_pass")
public class SmsPassword {

    @Id
    private Long smsId;

    @Column(name = "sms_user_id",unique = true,nullable = false)
    private Long userId;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "sms_user_id",referencedColumnName = "id",insertable = false,updatable = false)
    private User user;

    @JsonIgnore
    @Column(name = "sms_password")
    private String smsPass;

    @Column(name = "endPeriod", nullable = false)
    private Date endPeriod;

    public SmsPassword() {
    }

    public SmsPassword(Long smsId, Long userId, String smsPass, Date endPeriod) {
        this.smsId = smsId;
        this.userId = userId;
        this.smsPass = smsPass;
        this.endPeriod = endPeriod;

    }

    public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long id) {
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

    public Date getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
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

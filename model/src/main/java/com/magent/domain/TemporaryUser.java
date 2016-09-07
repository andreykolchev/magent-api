package com.magent.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.magent.domain.interfaces.Identifiable;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by artomov.ihor on 07.07.2016.
 */
@Entity
@Table(name = "ma_temporary_user")
public class TemporaryUser implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "temp_usr_pk")
    @JsonIgnore
    private Long id;

    @Column(name = "login",nullable = false, unique = true)
    private String username;

    @Column(name = "tmp_pwd", nullable = false)
    private String hashedPwd;

    @Column(name = "tmp_usr_device_id",nullable = false,unique = true)
    private String devicesId;

    @Column(name = "tmp_first_name")
    private String firstName;

    @Column(name = "tmp_last_name")
    private String lastName;

    @Column(name = "tmp_e_mail",nullable = false,unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "tmp_otp",nullable = false)
    private String hashedOtp;

    @JsonIgnore
    @Column(name = "tmp_confirm_expiry",nullable = false)
    private Date endPeriod;

    public TemporaryUser() {
    }
    //pwd must be hashed 2 times
    //otp hashed 1 time
    public TemporaryUser(TemporaryUser temporaryUser,Date endPeriod,String hashedOtp,String hashedPwd){
        this.devicesId=temporaryUser.getDevicesId();
        this.email=temporaryUser.getEmail();
        this.endPeriod=endPeriod;
        this.firstName=temporaryUser.getFirstName();
        this.hashedOtp=hashedOtp;
        this.lastName=temporaryUser.getLastName();
        this.username =temporaryUser.getUsername();
        this.hashedPwd = hashedPwd;
    }

    public TemporaryUser(String devicesId, String email, String firstName, String lastName, String username, String hashedPwd){
        this.devicesId=devicesId;
        this.email=email;
        this.firstName=firstName;
        this.lastName=lastName;
        this.username = username;
        this.hashedPwd = hashedPwd;
        this.endPeriod=new Date();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getHashedPwd() {
        return hashedPwd;
    }

    public void setHashedPwd(String password) {
        this.hashedPwd = password;
    }

    public String getDevicesId() {
        return devicesId;
    }

    public void setDevicesId(String devices) {
        this.devicesId = devices;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedOtp() {
        return hashedOtp;
    }

    public void setHashedOtp(String hashedOtp) {
        this.hashedOtp = hashedOtp;
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

        TemporaryUser that = (TemporaryUser) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

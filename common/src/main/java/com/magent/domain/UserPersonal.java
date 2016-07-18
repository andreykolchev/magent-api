package com.magent.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

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

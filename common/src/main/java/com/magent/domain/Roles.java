package com.magent.domain;

import com.magent.domain.enums.UserRoles;

import javax.persistence.*;

/**
 * Created  on 07.06.2016.
 */
@Entity
@Table(name = "ma_user_roles")
public class Roles {

    @Id
    private Long id;

    @Column(name = "role",updatable = false,nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoles roles;

    public Roles(UserRoles roles) {
        this.id=roles.getRoleId();
        this.roles = roles;
    }

    public Roles() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRoles getRoles() {
        return roles;
    }

    public void setRoles(UserRoles roles) {
        this.roles = roles;
    }
}

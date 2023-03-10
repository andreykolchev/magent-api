package com.magent.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.magent.domain.enums.UserRoles;
import com.magent.domain.interfaces.Identifiable;
import javassist.NotFoundException;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "ma_user")
public class User implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "usr_pk")
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(name = "u_role", nullable = false)
    private Long role;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "u_role", referencedColumnName = "usr_rol_pk", insertable = false, updatable = false)
    private Roles uRole;

    @Column
    private boolean enabled;

    @Transient
    private Date expirationDate;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(
            name = "ma_user_device",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "usr_pk")},
            inverseJoinColumns = {@JoinColumn(name = "device_id", referencedColumnName = "device_pk")})
    private List<Device> devices;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonBackReference(value = "account")
    private Account account;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "e_mail")
    private String email;

    @JsonIgnore
    @OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE)
    @JsonBackReference(value = "userPersonal")
    private UserPersonal userPersonal;

    public User() {
    }

    public User(TemporaryUser temporaryUser) {
        this.login=temporaryUser.getUsername();
        this.email=temporaryUser.getEmail();
        this.role=UserRoles.SALES_AGENT_FREELANCER_LEAD_GEN.getRoleId();
        this.enabled=true;
        this.firstName=temporaryUser.getFirstName();
        this.lastName=temporaryUser.getLastName();
        this.devices=new ArrayList<>(Arrays.asList(new Device(temporaryUser.getDevicesId())));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserRoles getRole() throws NotFoundException {
        return UserRoles.getById(role);
    }

    public void setRole(UserRoles role) {
        this.role =role.getRoleId();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
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

    public UserPersonal getUserPersonal() {
        return userPersonal;
    }

    public void setUserPersonal(UserPersonal userPersonal) {
        this.userPersonal = userPersonal;
    }

    public boolean addDevice(Device curDevice) {
        if (devices == null) {
            devices = new ArrayList<>();
        }
        for (Device dev : devices) {
            if (dev.getId().equals(curDevice.getId())) {
                return false;
            }
        }
        devices.add(curDevice);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return Objects.equals(id, that.id);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", role='" + role + '\'' +
                ", enabled=" + enabled +
                ", expirationDate=" + expirationDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

package com.magent.domain;

import com.magent.domain.interfaces.ChangeableEntity;
import com.magent.domain.interfaces.Identifiable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ma_device")
public class Device implements ChangeableEntity, Identifiable<String> {

    @Id
    @Column(name = "id")
    private String deviceId;

    @Column
    private String googleId;

    @Column
    private Long lastChange;


    @ManyToMany(mappedBy = "devices", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<User> users;


    @Override
    public Long getLastChange() {
        return this.lastChange;
    }

    @Override
    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }


    public String getId() {
        return deviceId;
    }

    public void setId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }

        for (User existingUsers : users) {
            if (existingUsers.getId().equals(user.getId())) {
                return false;
            }
        }

        users.add(user);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device that = (Device) o;
        return Objects.equals(deviceId, that.deviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId);
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceId='" + deviceId + '\'' +
                ", googleId='" + googleId + '\'' +
                '}';
    }

}

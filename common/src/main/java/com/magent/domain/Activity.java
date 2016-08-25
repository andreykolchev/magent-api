package com.magent.domain;


import com.magent.domain.interfaces.Identifiable;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ma_activity")
public class Activity implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "activity_pk")
    private Long id;

    @Column(name = "app_name",nullable = false)
    private String appName;

    @Column(name = "app_duration")
    private Long appDuration;

    @Column(name = "app_date")
    private Date appDate;

    @Column(name = "device_id")
    private String deviceId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "device_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_device_id"))
    private Device device;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_user_id"))
    private User user;

    @Version
    private int version;

    public Activity() {
    }

    public Activity(String deviceId, Long userId, Date appDate, String appName, Long appDuration) {
        this.deviceId = deviceId;
        this.userId = userId;
        this.appDate = appDate;
        this.appName = appName;
        this.appDuration = appDuration;
    }

    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long aLong) {
        this.id = aLong;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getAppDuration() {
        return appDuration;
    }

    public void setAppDuration(Long appDuration) {
        this.appDuration = appDuration;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        return id.equals(activity.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

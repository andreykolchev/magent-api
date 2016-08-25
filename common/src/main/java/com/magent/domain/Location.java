package com.magent.domain;

import com.magent.domain.interfaces.Identifiable;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="ma_location")
public class Location implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "loc_pk")
    private Long id;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "location_date")
    private Date LocationDate;


    @Column(name = "device_id")
    private String deviceId;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name="device_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_device_id"))
    private Device device;


    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name="user_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_user_id"))
    private User user;

    public Location() {
    }

    public Location(Double latitude, Double longitude, Date locationDate, String deviceId, Long userId) {
        this.latitude = latitude;
        this.longitude = longitude;
        LocationDate = locationDate;
        this.deviceId = deviceId;
        this.userId = userId;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Date getLocationDate() {
        return LocationDate;
    }

    public void setLocationDate(Date locationDate) {
        LocationDate = locationDate;
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
        Location that = (Location) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", LocationDate=" + LocationDate +
                ", deviceId='" + deviceId + '\'' +
                ", userId=" + userId +
                '}';
    }
}

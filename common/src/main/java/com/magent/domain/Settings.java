package com.magent.domain;

import com.magent.domain.interfaces.Identifiable;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="ma_settings")
public class Settings implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "sett_pk")
    private Long id;

    @Column(name = "is_upload_location")
    private Boolean isUploadLocation;

    @Column(name = "is_upload_calls")
    private Boolean isUploadCalls;

    @Column(name = "is_upload_apps")
    private Boolean isUploadApps;

    @Column(name = "upload_stats_start_time")
    private Long uploadStatsStartTime;

    @Column(name = "upload_stats_stop_time")
    private Long uploadStatsStopTime;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name="user_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_user_id"))
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getUploadLocation() {
        return isUploadLocation;
    }

    public void setUploadLocation(Boolean uploadLocation) {
        isUploadLocation = uploadLocation;
    }

    public Boolean getUploadCalls() {
        return isUploadCalls;
    }

    public void setUploadCalls(Boolean uploadCalls) {
        isUploadCalls = uploadCalls;
    }

    public Boolean getUploadApps() {
        return isUploadApps;
    }

    public void setUploadApps(Boolean uploadApps) {
        isUploadApps = uploadApps;
    }

    public Long getUploadStatsStartTime() {
        return uploadStatsStartTime;
    }

    public void setUploadStatsStartTime(Long uploadStatsStartTime) {
        this.uploadStatsStartTime = uploadStatsStartTime;
    }

    public Long getUploadStatsStopTime() {
        return uploadStatsStopTime;
    }

    public void setUploadStatsStopTime(Long uploadStatsStopTime) {
        this.uploadStatsStopTime = uploadStatsStopTime;
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
        Settings that = (Settings) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Settings{" +
                "id=" + id +
                ", isUploadLocation=" + isUploadLocation +
                ", isUploadCalls=" + isUploadCalls +
                ", isUploadApps=" + isUploadApps +
                ", uploadStatsStartTime=" + uploadStatsStartTime +
                ", uploadStatsStopTime=" + uploadStatsStopTime +
                ", userId=" + userId +
                '}';
    }
}

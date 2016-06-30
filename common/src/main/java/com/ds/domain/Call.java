package com.ds.domain;


import com.ds.domain.interfaces.ChangeableEntity;
import com.ds.domain.interfaces.Identifiable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="ds_call")
public class Call implements ChangeableEntity,Identifiable<Long> {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "call_number")
    private String callNumber;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "call_duration")
    private Integer callDuration;

    @Column(name = "call_date")
    private Date callDate;

    @Column(name = "call_type")
    private String callType;

    @Column
    private Long lastChange;


    @Column(name = "device_id")
    private String deviceId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name="device_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_device_id"))
    private Device device;


    @Column(name = "user_id")
    private Long userId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name="user_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_user_id"))
    private User user;


    @Column(name = "assignment_id")
    private Long assignmentId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name="assignment_id",referencedColumnName = "id",nullable = true,insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_assignment_id"))
    @JsonBackReference(value = "assignmentCalls")
    private Assignment assignment;


    @Override
    public Long getLastChange() {
        return this.lastChange;
    }

    @Override
    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }

    public Call() {
    }

    public Call(String callNumber, String contactName, Integer callDuration, Date callDate, String callType, Long lastChange, String deviceId, Long userId, Long assignmentId) {
        this.callNumber = callNumber;
        this.contactName = contactName;
        this.callDuration = callDuration;
        this.callDate = callDate;
        this.callType = callType;
        this.lastChange = lastChange;
        this.deviceId = deviceId;
        this.userId = userId;
        this.assignmentId = assignmentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    private transient int version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version=version;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Integer getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Integer callDuration) {
        this.callDuration = callDuration;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
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

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Call that = (Call) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}

package com.ds.domain;


import com.ds.domain.interfaces.Identifiable;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ds_assignment")
public class Assignment implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "datestart")
    private Date dateStart;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "dateend")
    private Date dateEnd;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "deadline")
    private Date deadLine;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "description")
    private String desc;

    @Column(name = "status",nullable = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Enumerated(value = EnumType.STRING)
    private AssignmentStatus status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "latitude")
    private Double latitude;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "longitude")
    private Double longitude;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "lastchange")
    private Long lastChange;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    //@Version
    @Column(name = "version", nullable = true)
    private int version;

    @Column(name = "template_id", nullable = false)
    private Long templateId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_template_id"))
    private Template template;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id",insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_user_id"))
    private User user;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "reason_id", nullable = true)
    private Long reasonId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "reason_id",referencedColumnName = "id",insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_reason_id"))
    private Reason reason;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "assignment", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<AssignmentAttribute> attributes;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "assignment", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<AssignmentTask> tasks;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "assignment",fetch = FetchType.LAZY)
    private List<Call> callList;



    public Assignment() {}

    public Assignment(Assignment assignment) {
        setId(assignment.getId());
        setTemplateId(assignment.getTemplateId());
        setUserId(assignment.getUserId());
        setReasonId(assignment.getReasonId());
        setDateStart(assignment.getDateStart());
        setDateEnd(assignment.getDateEnd());
        setDeadLine(assignment.getDeadLine());
        setDesc(assignment.getDesc());
        setStatus(assignment.getStatus());
        setLatitude(assignment.getLatitude());
        setLongitude(assignment.getLongitude());
    }

    @PreRemove
    private void delete(){
        for (Call call:callList){
            call.setAssignmentId(null);
        }
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
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

    public Long getLastChange() {
        return lastChange;
    }

    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
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

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public Set<AssignmentAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<AssignmentAttribute> attributes) {
        this.attributes = attributes;
    }

    public Set<AssignmentTask> getTasks() {
        return tasks;
    }

    public void setTasks(Set<AssignmentTask> tasks) {
        this.tasks = tasks;
    }

    public List<Call> getCallList() {
        return callList;
    }

    public void setCallList(List<Call> callList) {
        this.callList = callList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
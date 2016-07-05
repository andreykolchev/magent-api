package com.magent.domain;

import com.magent.domain.interfaces.ChangeableEntity;
import com.magent.domain.interfaces.Identifiable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ma_assignment_tasks")
public class AssignmentTask implements ChangeableEntity,Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "description")
    private String desc;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "required")
    private Boolean required;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "priority")
    private Integer priority;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column
    private Long lastChange;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Version
    private int version;

    @Column(name = "assignment_id")
    private Long assignmentId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "assignment_id",referencedColumnName = "id",nullable = false, insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_assignment_id"))
    @JsonBackReference(value = "assignment")
    private Assignment assignment;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "assignmentTask", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<AssignmentTaskControl> controls;


    @Override
    public Long getLastChange() {
        return lastChange;
    }

    @Override
    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }


    public AssignmentTask() {}

    public AssignmentTask(Long assignmentId, TemplateTask templateTask) {

        setAssignmentId(assignmentId);
        setDesc(templateTask.getDesc());
        setRequired(templateTask.getRequired());
        setPriority(templateTask.getPriority());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(int version) {
    this.version=version;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignId) {
        this.assignmentId = assignId;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Set<AssignmentTaskControl> getControls() {
        return controls;
    }

    public void setControls(Set<AssignmentTaskControl> controls) {
        this.controls = controls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentTask that = (AssignmentTask) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AssignmentTask{" +
                "id=" + id +
                ", desc=" + desc +
                ", required=" + required +
                ", priority=" + priority +
                ", assignmentId=" + assignmentId +
                '}';
    }

}

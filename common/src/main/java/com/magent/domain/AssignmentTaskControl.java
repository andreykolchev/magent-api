package com.magent.domain;

import com.magent.domain.interfaces.ChangeableEntity;
import com.magent.domain.interfaces.Identifiable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ma_assignment_task_controls")
public class AssignmentTaskControl implements ChangeableEntity,Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "asign_tc_pk")
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "description")
    private String desc;

    @Column(name = "value_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ValueType valueType;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "validation_rule")
    private String validationRule;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "control_value")
    private String value;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column
    private Boolean required;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column
    private Integer priority;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column
    private Long lastChange;

    @Column(name = "assignment_task_id")
    private Long assignmentTaskId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "assignment_task_id", nullable = false, insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_assignment_task_id"))
    @JsonBackReference(value = "assignmentTask")
    private AssignmentTask assignmentTask;



    @Override
    public Long getLastChange() {
        return this.lastChange;
    }

    @Override
    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }


    public AssignmentTaskControl() {}

    public AssignmentTaskControl(Long assignmentTaskId, TemplateTaskControl templateTaskControl) {

        setAssignmentTaskId(assignmentTaskId);
        setValueType(templateTaskControl.getValueType());
        setValidationRule(templateTaskControl.getValidationRule());
        setRequired(templateTaskControl.getRequired());
        setDesc(templateTaskControl.getDesc());
        setPriority(templateTaskControl.getPriority());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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


    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType controlValueType) {
        this.valueType = controlValueType;
    }

    public String getValidationRule() {
        return validationRule;
    }

    public void setValidationRule(String controlValidationRule) {
        this.validationRule = controlValidationRule;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Long getAssignmentTaskId() {
        return assignmentTaskId;
    }

    public void setAssignmentTaskId(Long assignmentTaskId) {
        this.assignmentTaskId = assignmentTaskId;
    }

    public AssignmentTask getAssignmentTask() {
        return assignmentTask;
    }

    public void setAssignmentTask(AssignmentTask assignmentTask) {
        this.assignmentTask = assignmentTask;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentTaskControl that = (AssignmentTaskControl) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AssignmentTaskControl{" +
                "id=" + id +
                ", desc=" + desc +
                ", valueType=" + valueType +
                ", validationRule=" + validationRule +
                ", value='" + value + '\'' +
                ", required=" + required +
                ", priority=" + priority +
                ", assignmentTaskId=" + assignmentTaskId +
                '}';
    }
}

package com.ds.domain;


import com.ds.domain.interfaces.ChangeableEntity;
import com.ds.domain.interfaces.Identifiable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ds_assignment_attribute")
public class AssignmentAttribute implements ChangeableEntity,Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "description")
    private String desc;

    @Column(name = "attr_name")
    private String name;

    @Column(name = "value_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ValueType valueType;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "attr_value")
    private String value;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column
    private Boolean required;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column
    private Boolean editable;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column
    private Boolean visible;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column
    private Long lastChange;

    @Column(name = "assignment_id")
    private Long assignmentId;


    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id",referencedColumnName = "id", nullable = false, insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_assignment_id"))
    @JsonBackReference(value = "assignmentAttributes")
    private Assignment assignment;


    @Override
    public Long getLastChange() {
        return this.lastChange;
    }

    @Override
    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }


    public AssignmentAttribute() {}

    public AssignmentAttribute(Long assignmentId, TemplateAttribute templateAttribute) {

        setAssignmentId(assignmentId);
        setValueType(templateAttribute.getValueType());
        setRequired(templateAttribute.getRequired());
        setDesc(templateAttribute.getDesc());
        setEditable(templateAttribute.getEditable());
        setVisible(templateAttribute.getVisible());
        setName(templateAttribute.getName());
        setValue(templateAttribute.getValue());

   }

    public AssignmentAttribute(String desc, String name, ValueType valueType, String value, Boolean required, Boolean editable, Boolean visible, Long lastChange, Long assignmentId) {
        this.desc = desc;
        this.name = name;
        this.valueType = valueType;
        this.value = value;
        this.required = required;
        this.editable = editable;
        this.visible = visible;
        this.lastChange = lastChange;
        this.assignmentId = assignmentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setValueType(ValueType attributeValueType) {
        this.valueType = attributeValueType;
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

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



 @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentAttribute that = (AssignmentAttribute) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AssignmentAttribute{" +
                "id=" + id +
                ", name='" + name +
                ", desc='" + desc +
                ", attributeValueType='" + valueType +
                ", value='" + value +
                ", required=" + required +
                ", editable=" + editable +
                ", visible=" + visible +
                ", assignmentId=" + assignmentId +
                '}';
    }
}

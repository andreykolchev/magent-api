package com.ds.domain;

import com.ds.domain.interfaces.Identifiable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ds_template_task_controls")
public class TemplateTaskControl implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String desc;

    @Column(name = "value_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ValueType valueType;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "validation_rule")
    private String validationRule;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column
    private Boolean required;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column
    private Integer priority;

    @Column(name = "template_task_id")
    private Long templateTaskId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "template_task_id", nullable = false, insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_template_task_id"))
    @JsonBackReference(value = "controls")
    private TemplateTask templateTask;

    public TemplateTaskControl() {
    }

    public TemplateTaskControl(String desc, ValueType valueType, String validationRule, Boolean required, Integer priority, Long templateTaskId) {
        this.desc = desc;
        this.valueType = valueType;
        this.validationRule = validationRule;
        this.required = required;
        this.priority = priority;
        this.templateTaskId = templateTaskId;
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

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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

    public Long getTemplateTaskId() {
        return templateTaskId;
    }

    public void setTemplateTaskId(Long templateTaskId) {
        this.templateTaskId = templateTaskId;
    }

    public TemplateTask getTemplateTask() {
        return templateTask;
    }

    public void setTemplateTask(TemplateTask templateTask) {
        this.templateTask = templateTask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateTaskControl that = (TemplateTaskControl) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TemplateTaskControl{" +
                "id=" + id +
                ", desc=" + desc +
                ", valueType=" + valueType +
                ", validationRule=" + validationRule +
                ", required=" + required +
                ", priority=" + priority +
                ", templateTaskId=" + templateTaskId +
                '}';
    }
}

package com.ds.domain;

import com.ds.domain.interfaces.Identifiable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ds_template_attribute")
public class TemplateAttribute implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "description")
    private String desc;

    @Column(name = "value_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ValueType valueType;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column
    private Boolean required;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column
    private Boolean editable;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column
    private Boolean visible;

    @Column(name = "template_id", nullable = false)
    private Long templateId;

    @Column(name = "name")
    private String name;
    
    @Column(name = "value")
    private String value;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "template_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_template_id"))
    @JsonBackReference(value = "templateAttributes")
    private Template template;

    public TemplateAttribute() {
    }

    public TemplateAttribute(String desc, ValueType valueType, Boolean required, Boolean editable, Boolean visible, Long templateId) {
        this.desc = desc;
        this.valueType = valueType;
        this.required = required;
        this.editable = editable;
        this.visible = visible;
        this.templateId = templateId;
    }

    public TemplateAttribute(String desc, String name, ValueType valueType, String value, Boolean required, Boolean editable, Boolean visible, Long templateId ) {
        this.name=name;
        this.desc = desc;
        this.valueType = valueType;
        this.required = required;
        this.editable = editable;
        this.visible = visible;
        this.templateId = templateId;
        this.value=value;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateAttribute that = (TemplateAttribute) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TemplateAttribute{" +
                "id=" + id +
                ", name='" + name +
                ", desc='" + desc +
                ", valueType='" + valueType +
                ", required=" + required +
                ", editable=" + editable +
                ", visible=" + visible +
                ", templateId=" + templateId +
                '}';
    }
}

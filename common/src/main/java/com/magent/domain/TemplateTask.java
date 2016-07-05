package com.magent.domain;

import com.magent.domain.interfaces.Identifiable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ma_template_tasks")
public class TemplateTask implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String desc;

    @Column(name = "required")
    private Boolean required;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "template_id")
    private Long templateId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "template_id", nullable = false, insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_template_id"))
    @JsonBackReference(value = "template")
    private Template template;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "templateTask", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<TemplateTaskControl> controls;

    public TemplateTask() {
    }

    public TemplateTask(String desc, Boolean required, Integer priority, Long templateId) {
        this.desc = desc;
        this.required = required;
        this.priority = priority;
        this.templateId = templateId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<TemplateTaskControl> getControls() {
        return controls;
    }

    public void setControls(Set<TemplateTaskControl> controls) {
        this.controls = controls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateTask that = (TemplateTask) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TemplateTask{" +
                "id=" + id +
                ", desc=" + desc +
                ", required=" + required +
                ", priority=" + priority +
                ", templateId=" + templateId +
                '}';
    }
}

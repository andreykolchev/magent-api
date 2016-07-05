package com.magent.domain;

import com.magent.domain.interfaces.Identifiable;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ma_template")
public class Template implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "description")
    private String desc;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<TemplateAttribute> attributes;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<TemplateTask> templateTasks;

    public Template() {
    }

    public Template(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Set<TemplateAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<TemplateAttribute> attributes) {
        this.attributes = attributes;
    }

    public Set<TemplateTask> getTemplateTasks() {
        return templateTasks;
    }

    public void setTemplateTasks(Set<TemplateTask> templateTasks) {
        this.templateTasks = templateTasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template that = (Template) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Template{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}

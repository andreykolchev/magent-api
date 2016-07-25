package com.magent.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.magent.domain.interfaces.Identifiable;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ma_template")
public class Template implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "templ_pk")
    @ApiModelProperty(required = false)
    private Long id;
    @ApiModelProperty(required = true)
    @Column(nullable = false)
    private String name;
    @ApiModelProperty(required = true)
    @Column(name = "description")
    private String desc;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<TemplateAttribute> attributes;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<TemplateTask> templateTasks;

    @Column(name = "tmp_tmp_type_id",nullable = false,updatable = false,unique = true)
    private Long templateTypeId;


    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "tmp_tmp_type_id",referencedColumnName = "temp_type_pk",insertable = false,updatable = false)
    @ApiModelProperty(required = false,readOnly = true,hidden = true)
    private TemplateType templateType;

    public Template() {
    }

    public Template(String name, String desc,Long templateTypeId) {
        this.name = name;
        this.desc = desc;
        this.templateTypeId = templateTypeId;
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

    public Long getTemplateTypeId() {
        return templateTypeId;
    }

    public void setTemplateTypeId(Long teplateTypeId) {
        this.templateTypeId = teplateTypeId;
    }

    public TemplateType getTemplateType() {
        return templateType;
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

package com.magent.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magent.domain.enums.UserRoles;
import com.magent.domain.interfaces.Identifiable;
import javassist.NotFoundException;

import javax.persistence.*;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created on 19.07.2016.
 */
@Entity
@Table(name = "ma_template_types")
public class TemplateType implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "temp_type_pk")
    private Long id;

    @Column(name = "temp_type_desc", nullable = false)
    private String description;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(
            name = "ma_tmp_types_roles",
            joinColumns = {@JoinColumn(name = "temp_type_pk",nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "usr_rol_pk",nullable = false)})
    private List<Roles> roles;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "parent_temp_tp_pk", nullable = true)
    private Long parentId;


    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "parent_temp_tp_pk", referencedColumnName = "temp_type_pk", insertable = false, updatable = false)
    @JsonBackReference(value = "parentTmpType")
    private Set<TemplateType> childTemplatesTypes;

    @JsonIgnore
    @OneToOne(mappedBy = "templateType")
    private Template template;

    @Transient
    @JsonSerialize
    @JsonDeserialize
    private Long templateId;

    @Transient
    @JsonSerialize
    @JsonDeserialize
    private List<UserRoles> userRolesList;



    public TemplateType() {

    }

    public TemplateType(String description, List<UserRoles> userRolesList) {
        this.description = description;
        this.userRolesList = userRolesList;
    }

    public TemplateType(String description, Long parentId, List<UserRoles> userRolesList) {
        this.description = description;
        this.parentId = parentId;
        this.userRolesList = userRolesList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public List<UserRoles> getUserRolesList() throws NotFoundException {
        if (Objects.isNull(userRolesList))return UserRoles.getUserRoles(roles);
        return userRolesList;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Set<TemplateType> getChildTemplatesTypes() {
        return childTemplatesTypes;
    }

    public void setUserRolesList(List<UserRoles> userRolesList) {
        this.userRolesList = userRolesList;
    }

    public Template getTemplate() {
        return template;
    }

    public void setChildTemplatesTypes(Set<TemplateType> childTemplatesTypes) {
        this.childTemplatesTypes = childTemplatesTypes;
    }

    public Long getTemplateId() {
        if (Objects.nonNull(getTemplate()))this.templateId=getTemplate().getId();
        return templateId;
    }

    @PrePersist
    private void preSave() throws ValidationException {
        if (Objects.isNull(userRolesList)||userRolesList.size()==0)throw new ValidationException("type must contain role");
        setRoles(UserRoles.getRoles(userRolesList));
    }

    @PostPersist
    private void postPersist() throws NotFoundException {
        if (Objects.isNull(userRolesList)) setUserRolesList(UserRoles.getUserRoles(roles));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateType that = (TemplateType) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "TemplateType{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}

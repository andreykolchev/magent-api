package com.magent.domain;

import com.magent.domain.interfaces.Identifiable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ma_reason")
public class Reason implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "reason_pk")
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "parent_id", referencedColumnName = "reason_pk", nullable = false, insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_parent_id"))
    @JsonBackReference(value = "parent")
    private Reason parent;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "parent",cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JsonBackReference(value = "child")
    private List<Reason>child;

    @Column
    private String name;

    @Column(name = "description")
    private String desc;

    public Reason() {
    }

    public Reason(Long parentId, String name, String desc) {
        this.parentId = parentId;
        this.name = name;
        this.desc = desc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Reason getParent() {
        return parent;
    }

    public void setParent(Reason parent) {
        this.parent = parent;
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

    public List<Reason> getChild() {
        return child;
    }

    public void setChild(List<Reason> child) {
        this.child = child;
    }

    @PreRemove
    private void onDelete(){
        for (Reason reason:child)reason.setParentId(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reason that = (Reason) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

package com.poly.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "quantity_managerment")
public class QuantityManagerment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "product_color_id", nullable = false)
    private Integer productColorId;
    @Basic
    @Column(name = "changed_by", nullable = false)
    private Integer changedBy;
    @Basic
    @Column(name = "time_changed", nullable = false)
    private Timestamp timeChanged;
    @Basic
    @Column(name = "note", nullable = false, length = 30)
    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductColorId() {
        return productColorId;
    }

    public void setProductColorId(Integer productColorId) {
        this.productColorId = productColorId;
    }

    public Integer getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Integer changedBy) {
        this.changedBy = changedBy;
    }

    public Timestamp getTimeChanged() {
        return timeChanged;
    }

    public void setTimeChanged(Timestamp timeChanged) {
        this.timeChanged = timeChanged;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }



    @ManyToOne
    @JoinColumn(name = "changed_by", insertable = false ,updatable  = false)
    @JsonIgnore
    Account account;
    @ManyToOne
    @JoinColumn(name = "product_color_id", insertable = false ,updatable  = false)
    @JsonIgnore
    ProductColor productColor;

    public Account getAccount() {
        return account;
    }

    public ProductColor getProductColor() {
        return productColor;
    }
}

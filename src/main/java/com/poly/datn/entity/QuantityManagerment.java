package com.poly.datn.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Quantity_Managerment", schema = "bhoddvjk1na7d8a0xtlr")
public class QuantityManagerment {
    private Integer id;
    private Integer productColorId;
    private Integer changedBy;
    private Timestamp timeChanged;
    private String note;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "product_color_id", nullable = false)
    public int getProductColorId() {
        return productColorId;
    }

    public void setProductColorId(int productColorId) {
        this.productColorId = productColorId;
    }

    @Basic
    @Column(name = "changed_by", nullable = false)
    public int getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(int changedBy) {
        this.changedBy = changedBy;
    }

    @Basic
    @Column(name = "time_changed", nullable = false)
    public Timestamp getTimeChanged() {
        return timeChanged;
    }

    public void setTimeChanged(Timestamp timeChanged) {
        this.timeChanged = timeChanged;
    }

    @Basic
    @Column(name = "note", nullable = false, length = 30)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }



    @ManyToOne
    @JoinColumn(name = "changed_by")
    Account account;
    @ManyToOne
    @JoinColumn(name = "product_color_id")
    ProductColor productColor;
}

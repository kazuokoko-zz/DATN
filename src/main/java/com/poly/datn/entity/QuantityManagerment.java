package com.poly.datn.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Quantity_Managerment")
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductColorId() {
        return productColorId;
    }

    public void setProductColorId(int productColorId) {
        this.productColorId = productColorId;
    }

    public int getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(int changedBy) {
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
    @JoinColumn(name = "changed_by")
    Account account;
    @ManyToOne
    @JoinColumn(name = "product_color_id")
    ProductColor productColor;

    public Account getAccount() {
        return account;
    }

    public ProductColor getProductColor() {
        return productColor;
    }
}

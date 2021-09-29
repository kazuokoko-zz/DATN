package com.poly.datn.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Warranty_invoice", schema = "bhoddvjk1na7d8a0xtlr", catalog = "")
public class WarrantyInvoice {
    private int id;
    private Integer warrantyInvoice;
    private String productState;
    private byte type;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "warranty_invoice", nullable = true)
    public Integer getWarrantyInvoice() {
        return warrantyInvoice;
    }

    public void setWarrantyInvoice(Integer warrantyInvoice) {
        this.warrantyInvoice = warrantyInvoice;
    }

    @Basic
    @Column(name = "product_state", nullable = false, length = -1)
    public String getProductState() {
        return productState;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarrantyInvoice that = (WarrantyInvoice) o;
        return id == that.id && type == that.type && Objects.equals(warrantyInvoice, that.warrantyInvoice) && Objects.equals(productState, that.productState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, warrantyInvoice, productState, type);
    }
}

package com.poly.datn.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Warranty_invoice", schema = "bhoddvjk1na7d8a0xtlr")
public class WarrantyInvoice {
    private Integer id;
    private Integer warrantyInvoice;
    private String productState;
    private Boolean type;

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
    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }


}

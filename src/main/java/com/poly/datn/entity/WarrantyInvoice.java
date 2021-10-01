package com.poly.datn.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Warranty_invoice")
public class WarrantyInvoice {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "warranty_invoice", nullable = true)
    private Integer warrantyInvoice;
    @Basic
    @Column(name = "product_state", nullable = false, length = -1)
    private String productState;
    @Basic
    @Column(name = "type", nullable = false)
    private Boolean type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getWarrantyInvoice() {
        return warrantyInvoice;
    }

    public void setWarrantyInvoice(Integer warrantyInvoice) {
        this.warrantyInvoice = warrantyInvoice;
    }

    public String getProductState() {
        return productState;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }


}

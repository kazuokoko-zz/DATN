package com.poly.datn.entity;

import javax.persistence.*;

@Entity
@Table(name = "warranty_invoice")
public class WarrantyInvoice {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "warranty_invoice", nullable = true)
    private Integer invoice;
    @Basic
    @Column(name = "product_state", nullable = false, length = -1)
    private String productState;
    @Basic
    @Column(name = "type", nullable = false)
    private Boolean type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWarrantyInvoice() {
        return invoice;
    }

    public void setWarrantyInvoice(Integer warrantyInvoice) {
        this.invoice = warrantyInvoice;
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

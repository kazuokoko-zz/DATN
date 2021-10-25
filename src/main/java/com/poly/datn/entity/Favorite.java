package com.poly.datn.entity;


import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "favorite")
public class Favorite {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "account_id", nullable = false)
    private Integer accountId;
    @Basic
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }


    @ManyToOne
    @JoinColumn(name = "account_id", insertable = false ,updatable  = false)
    Account account;
    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false ,updatable  = false)
    Product product;

    public Account getAccount() {
        return account;
    }

    public Product getProduct() {
        return product;
    }
}

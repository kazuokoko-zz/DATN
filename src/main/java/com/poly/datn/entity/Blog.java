package com.poly.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Blog")
public class Blog {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "title", nullable = false, length = 150)
    private String title;
    @Basic
    @Column(name = "short_text", nullable = false)
    private String shortText;
    @Basic
    @Column(name = "photo", nullable = false)
    private String photo;
    @Basic
    @Column(name = "time_created", nullable = false)
    private Timestamp timeCreated;
    @Basic
    @Column(name = "created_by", nullable = false, length = 30)
    private String createdBy;
    @Basic
    @Column(name = "type", nullable = false)
    private Integer type;
    @Basic
    @Column(name = "product_id")
    private Integer productId;
    @Basic
    @Column(name = "status", nullable = false)
    private Boolean status;


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    @ManyToOne
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    @JsonIgnore
    Account account;


    @OneToMany(mappedBy = "blog")
    List<Comment> comments;
    @OneToMany(mappedBy = "blog")
    List<BlogDetails> blogDetails;

    public Account getAccount() {
        return account;
    }


    public List<Comment> getComments() {
        return comments;
    }

    public List<BlogDetails> getBlogDetails() {
        return blogDetails;
    }
}

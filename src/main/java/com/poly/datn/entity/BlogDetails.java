package com.poly.datn.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "blog_details")
public class BlogDetails {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "blog_id", nullable = false)
    private Integer blogId;
    @Basic
    @Column(name = "type", nullable = false)
    private String type;
    @Basic
    @Column(name = "ordinal", nullable = false)
    private Short ordinal;
    @Basic
    @Column(name = "content", nullable = false, length = -1)
    private String content;


    @ManyToOne
    @JoinColumn(name = "blog_id", insertable = false, updatable = false)

    Blog blog;

    public Blog getBlog() {
        return blog;
    }
}

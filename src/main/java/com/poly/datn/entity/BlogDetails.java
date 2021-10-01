package com.poly.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Blog_details")
public class BlogDetails {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "blog_id", nullable = false)
    private Integer blogId;
    @Basic
    @Column(name = "type", nullable = false)
    private Short type;
    @Basic
    @Column(name = "ordinal", nullable = false)
    private Short ordinal;
    @Basic
    @Column(name = "content", nullable = false, length = -1)
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public short getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(short ordinal) {
        this.ordinal = ordinal;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @ManyToOne
    @JoinColumn(name = "blog_id", insertable = false ,updatable  = false)
    @JsonIgnore
    Blog blog;

    public Blog getBlog() {
        return blog;
    }
}

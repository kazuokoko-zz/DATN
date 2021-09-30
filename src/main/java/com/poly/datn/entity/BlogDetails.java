package com.poly.datn.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Blog_details", schema = "bhoddvjk1na7d8a0xtlr")
public class BlogDetails {
    private Long id;
    private Integer blogId;
    private Short type;
    private Short ordinal;
    private String content;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "blog_id", nullable = false)
    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    @Basic
    @Column(name = "ordinal", nullable = false)
    public short getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(short ordinal) {
        this.ordinal = ordinal;
    }

    @Basic
    @Column(name = "content", nullable = false, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @ManyToOne
    @JoinColumn(name = "blog_id")
    Blog blog;
}

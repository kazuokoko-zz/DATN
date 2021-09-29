package com.poly.datn.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Blog_details", schema = "bhoddvjk1na7d8a0xtlr", catalog = "")
public class BlogDetails {
    private long id;
    private int blogId;
    private byte type;
    private short ordinal;
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
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogDetails that = (BlogDetails) o;
        return id == that.id && blogId == that.blogId && type == that.type && ordinal == that.ordinal && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, blogId, type, ordinal, content);
    }
}

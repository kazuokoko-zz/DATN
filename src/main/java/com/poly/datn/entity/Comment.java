package com.poly.datn.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Comment {
    private long id;
    private int blogId;
    private Byte rate;
    private Timestamp timeCreated;
    private Long repliedTo;
    private String name;
    private String email;
    private byte status;
    private String comment;

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
    @Column(name = "rate", nullable = true)
    public Byte getRate() {
        return rate;
    }

    public void setRate(Byte rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "time_created", nullable = false)
    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Basic
    @Column(name = "replied_to", nullable = true)
    public Long getRepliedTo() {
        return repliedTo;
    }

    public void setRepliedTo(Long repliedTo) {
        this.repliedTo = repliedTo;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "comment", nullable = false, length = -1)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment1 = (Comment) o;
        return id == comment1.id && blogId == comment1.blogId && status == comment1.status && Objects.equals(rate, comment1.rate) && Objects.equals(timeCreated, comment1.timeCreated) && Objects.equals(repliedTo, comment1.repliedTo) && Objects.equals(name, comment1.name) && Objects.equals(email, comment1.email) && Objects.equals(comment, comment1.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, blogId, rate, timeCreated, repliedTo, name, email, status, comment);
    }
}

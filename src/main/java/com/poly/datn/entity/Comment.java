package com.poly.datn.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
public class Comment {
    private Long id;
    private Integer blogId;
    private Byte rate;
    private Timestamp timeCreated;
    private Long repliedTo;
    private String name;
    private String email;
    private Boolean status;
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
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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



    @ManyToOne
    @JoinColumn(name = "blog_id")
    Blog blog;

    @OneToMany(mappedBy = "reComment")
    List<Comment> comments;

    @ManyToOne
    @JoinColumn(name ="id")
    Comment reComment;
}

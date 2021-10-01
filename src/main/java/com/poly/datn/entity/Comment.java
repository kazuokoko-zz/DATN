package com.poly.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "blog_id", nullable = false)
    private Integer blogId;
    @Basic
    @Column(name = "rate", nullable = true)
    private Byte rate;
    @Basic
    @Column(name = "time_created", nullable = false)
    private Timestamp timeCreated;
    @Basic
    @Column(name = "replied_to", nullable = true)
    private Long repliedTo;
    @Basic
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @Basic
    @Column(name = "email", nullable = true, length = 50)
    private String email;
    @Basic
    @Column(name = "status", nullable = false)
    private Boolean status;
    @Basic
    @Column(name = "comment", nullable = false, length = -1)
    private String comment;

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

    public Byte getRate() {
        return rate;
    }

    public void setRate(Byte rate) {
        this.rate = rate;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Long getRepliedTo() {
        return repliedTo;
    }

    public void setRepliedTo(Long repliedTo) {
        this.repliedTo = repliedTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @ManyToOne
    @JoinColumn(name = "blog_id", insertable = false ,updatable  = false)
    @JsonIgnore
    Blog blog;

    @OneToMany(mappedBy = "reComment")
    List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "id", insertable = false ,updatable  = false)
    @JsonIgnore
    Comment reComment;

    public Blog getBlog() {
        return blog;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Comment getReComment() {
        return reComment;
    }
}

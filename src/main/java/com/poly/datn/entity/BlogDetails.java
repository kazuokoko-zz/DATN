package com.poly.datn.entity;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Data
@Entity
@Table(name = "blog_details")
public class BlogDetails implements Comparable<BlogDetails>{
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
    @Basic
    @Column(name = "stt", nullable = true)
    private String stt;


    @ManyToOne
    @JoinColumn(name = "blog_id", insertable = false, updatable = false)

    Blog blog;

    public Blog getBlog() {
        return blog;
    }


    @Override
    public int compareTo(@NotNull BlogDetails o) {
        return this.getStt().compareTo(o.getStt());
    }
}

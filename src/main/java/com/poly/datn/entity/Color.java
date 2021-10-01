package com.poly.datn.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Color {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "color_name", nullable = false, length = 15)
    private String colorName;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }



    @OneToMany(mappedBy = "color")
    List<ProductColor> productColors;

    public List<ProductColor> getProductColors() {
        return productColors;
    }
}
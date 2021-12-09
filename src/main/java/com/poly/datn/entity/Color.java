package com.poly.datn.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "color")
public class Color {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "color_name", nullable = false, length = 50)
    private String colorName;
    @Basic
    @Column(name = "code", nullable = false, length =8)
    private String code;



    @OneToMany(mappedBy = "color")
    List<ProductColor> productColors;

    public List<ProductColor> getProductColors() {
        return productColors;
    }
}

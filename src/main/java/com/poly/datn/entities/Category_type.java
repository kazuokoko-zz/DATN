package com.poly.datn.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the category_type database table.
 *
 */
@Data
@Entity
@NamedQuery(name="Category_type.findAll", query="SELECT u FROM Category_type u")
public class Category_type implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;
}

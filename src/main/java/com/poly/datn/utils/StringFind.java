package com.poly.datn.utils;

import com.poly.datn.entity.Product;

import java.util.List;

public class StringFind {
    private StringFind() {
    }


    public static List<Product> getMatch(List<Product> products, String sentences) {

        String[] words = sentences.trim().replaceAll("[._\\-]", " ").split(" ");

        products.forEach(product -> {
            Boolean found = false;
            for (String word : words) {
                if (product.getName().contains(word)) {
                    found = true;
                    break;
                }
            }
            if (Boolean.FALSE.equals(found)) {
                products.remove(product);
            }
        });
        return products;

    }
}

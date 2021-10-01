package com.poly.datn.Utils;

import com.poly.datn.entity.Product;

import java.util.List;

public class StringFind {


    public static List<Product> getMatch(List<Product> products, String sentences) {

        String[] words = sentences.trim().replaceAll("\\.|\\_|\\-", " ").split(" ");

        products.forEach(product -> {
            Boolean found = false;
            for (String word : words) {
                if (product.getName().contains(word)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                products.remove(product);
            }
        });
        return products;

    }
}

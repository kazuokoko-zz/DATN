package com.poly.datn.utils;

import com.poly.datn.entity.Blog;
import com.poly.datn.entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StringFind {

    public List<Product> getMatchProduct(List<Product> products, String sentences) {

        String[] words = sentences.trim().replaceAll("[._\\-]", " ").split(" ");
        List<Product> products1 = new ArrayList<>();
        for (Product product : products) {
            Boolean found = false;
            for (String word : words) {
                if (product.getName().toLowerCase().contains(word.toLowerCase())) {
                    found = true;
                    break;
                }
            }
            if (found) {
               products1.add(product);
            }
        }
        // products.forEach(product -> {
        //     Boolean found = false;
        //      for (String word : words) {
        //         if (product.getName().toLowerCase().contains(word.toLowerCase())) {
        //              found = true;
        //               break;
        //          }
        //       }
        //       if (Boolean.FALSE.equals(found)) {
        //           products.remove(product);
        //         }
        //    });
        return products1;

    }

    public List<Blog> getMatchBlog(List<Blog> blogs, String sentences) {

        String[] words = sentences.trim().replaceAll("[._\\-]", " ").split(" ");
        List<Blog> blogs1 = new ArrayList<>();
        blogs.forEach(blog -> {
            Boolean found = false;
            for (String word : words) {
                if (blog.getTitle().toLowerCase().contains(word.toLowerCase())) {
                    found = true;
                    break;
                }
            }
            if (found) {
                blogs1.add(blog);
            }
        });
        return blogs1;

    }
}

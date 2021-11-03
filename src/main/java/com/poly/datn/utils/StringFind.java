package com.poly.datn.utils;

import com.poly.datn.entity.Blog;
import com.poly.datn.entity.Product;

import java.util.List;

public class StringFind {
    private StringFind() {
    }


    public static List<Product> getMatchProduct(List<Product> products, String sentences) {

        String[] words = sentences.trim().replaceAll("[._\\-]", " ").split(" ");

        for(Product product : products)
        {
            Boolean found = false;
            for (String word : words) {
                if (product.getName().toLowerCase().contains(word.toLowerCase())) {
                    found = true;
                    break;
                }
            }
            if (Boolean.FALSE.equals(found)) {
                products.remove(product);
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
        return products;

    }

    public static List<Blog> getMatchBlog(List<Blog> blogs, String sentences) {

        String[] words = sentences.trim().replaceAll("[._\\-]", " ").split(" ");

        blogs.forEach(blog -> {
            Boolean found = false;
            for (String word : words) {
                if (blog.getTitle().toLowerCase().contains(word.toLowerCase())) {
                    found = true;
                    break;
                }
            }
            if (Boolean.FALSE.equals(found)) {
                blogs.remove(blog);
            }
        });
        return blogs;

    }
}

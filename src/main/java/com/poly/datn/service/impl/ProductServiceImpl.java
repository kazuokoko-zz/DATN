package com.poly.datn.service.impl;

import com.poly.datn.dao.*;
import com.poly.datn.entity.Blog;
import com.poly.datn.entity.BlogDetails;
import com.poly.datn.service.CommentService;
import com.poly.datn.utils.StringFind;
import com.poly.datn.vo.*;
import com.poly.datn.entity.Product;
import com.poly.datn.entity.ProductCategory;
import com.poly.datn.service.ProductService;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");

    @Autowired
    ProductDAO productDAO;

    @Autowired
    ProductCategoryDAO productCategoryDAO;

    @Autowired
    ProductColorDAO productColorDAO;

    @Autowired
    ProductDetailsDAO productDetailsDAO;

    @Autowired
    BlogDAO blogDAO;

    @Autowired
    BlogDetailsDAO blogDetailsDAO;

    @Autowired
    CommentService commentService;
    // Begin code of MA

    @Override
    public List<ProductVO> getList(Optional<Integer> cate, Optional<String> find) {
        List<Product> products;
        if (cate.isPresent() && find.isPresent()) {
            products = getListByCate(cate.get());
            products = StringFind.getMatchProduct(products, find.get());

        } else if (cate.isPresent()) {
            products = getListByCate(cate.get());
        } else if (find.isPresent()) {
            products = productDAO.findAll();
            products = StringFind.getMatchProduct(products, find.get());

        } else {
            products = productDAO.findAll();
        }

        List<ProductVO> productVOS = new ArrayList<>();
        products.forEach(product -> {
            ProductVO productVO = convertToVO(product);
            productVOS.add(productVO);
        });

        return productVOS;
    }

    @Override
    public List<Product> getListByCate(Integer cateId) {
        List<Product> products = new ArrayList<>();
        List<ProductCategory> productCategories = productCategoryDAO.findAllByCategoryIdEquals(cateId);
        for (ProductCategory productCategory : productCategories) {
            Optional<Product> optionalProduct = productDAO.findById(productCategory.getProductId());
            if (optionalProduct.isPresent()) {
                products.add(optionalProduct.get());
            }
        }
        return products;
    }

    @Override
    public ProductVO getById(Integer id) throws NullPointerException {
        Product product = productDAO.findById(id).orElseThrow(() -> new NullPointerException("Product not found with id: " + id));
        ProductVO productVO = convertToVO(product);
        try {
            productVO.setBlogs(getBlogByProductIdAndType(productVO.getId(), 1));
        } catch (NullPointerException e) {
            productVO.setBlogs(null);
        }
        return productVO;
    }

    private ProductVO convertToVO(Product product) {
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(product, productVO);
        List<ProductColorVO> productColorVOS = new ArrayList<>();
        productColorDAO.getByProductId(productVO.getId()).forEach(productColor -> {
            ProductColorVO productColorVO = new ProductColorVO();
            BeanUtils.copyProperties(productColor, productColorVO);
            productColorVOS.add(productColorVO);
        });
        productVO.setProductColors(productColorVOS);
        List<ProductDetailsVO> productDetailsVOS = new ArrayList<>();
        List<String> photos = new ArrayList<>();
        productDetailsDAO.getByProductId(productVO.getId()).forEach(productDetails -> {
            ProductDetailsVO productDetailsVO = new ProductDetailsVO();
            if (productDetails.getPropertyName().equalsIgnoreCase("photo")) {
                for (String photo : productDetails.getPropertyValue().split(",")) {
                    photos.add(photo.trim());
                }
            } else {
                BeanUtils.copyProperties(productDetails, productDetailsVO);
                productDetailsVOS.add(productDetailsVO);
            }
        });
        productVO.setProductDetails(productDetailsVOS);
        productVO.setPhotos(photos);
        return productVO;
    }


    private BlogVO getBlogByProductIdAndType(Integer productId, Integer type) throws NullPointerException {
        Blog blog = blogDAO.getByProductIdAndType(productId, type).orElseThrow(() -> new NullPointerException("Blog not Fount for product"));
        BlogVO blogVO = new BlogVO();
        List<BlogDetailsVO> blogDetailsVOS = new ArrayList<>();
        BeanUtils.copyProperties(blog, blogVO);
        blogVO.setTimeCreated(sdf.format(blog.getTimeCreated()));
        for (BlogDetails blogDetails : blogDetailsDAO.findByBlogId(blog.getId())) {
            BlogDetailsVO blogDetailsVO = new BlogDetailsVO();
            BeanUtils.copyProperties(blogDetails, blogDetailsVO);
            blogDetailsVOS.add(blogDetailsVO);
        }
        blogVO.setComments(commentService.getListByBlogId(blogVO.getId()));
        blogVO.setBlogDetails(blogDetailsVOS);
        return blogVO;
    }
    // End code of MA

}

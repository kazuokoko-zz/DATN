package com.poly.datn.service.impl;

import com.poly.datn.dao.*;
import com.poly.datn.entity.*;
import com.poly.datn.service.AutoTask.AutoTaskService;
import com.poly.datn.service.CommentService;
import com.poly.datn.service.ProductService;
import com.poly.datn.service.SaleService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.utils.PriceUtils;
import com.poly.datn.utils.ProductUtils;
import com.poly.datn.utils.StringFind;
import com.poly.datn.vo.*;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
    @Autowired
    ProductUtils productUtils;

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
    StringFind stringFind;

    @Autowired
    CommentService commentService;

    @Autowired
    SaleService saleService;

    @Autowired
    CheckRole checkRole;

    @Autowired
    SaleDAO saleDAO;

    @Autowired
    ProductSaleDAO productSaleDAO;

    @Autowired
    PriceUtils priceUtils;
    // Begin code of MA

    @Autowired
    CategoryDAO categoryDAO;

    public List<ProductVO> getListP(Optional<Integer> cate, Optional<String> find){
        List<Product> products;
        if (cate.isPresent() && find.isPresent()) {
            products = getListByCate(cate.get());
            if (products.size() > 0)
                products = stringFind.getMatchProduct(products, find.get());
            else
                products = new ArrayList<>();
        } else if (cate.isPresent()) {
            products = getListByCate(cate.get());
        } else if (find.isPresent()) {
            products = productDAO.findAll();
            if (products.size() > 0)
                products = stringFind.getMatchProduct(products, find.get());
            else
                products = new ArrayList<>();
        } else {
            products = productDAO.findAll();
        }

        List<ProductVO> productVOS = new ArrayList<>();
        for (Product product : products) {

            ProductVO productVO = productUtils.convertToVO(product);
            productVOS.add(productVO);
        }
        return productVOS;
    }

    @Override
    public List<ProductVO> getList(Optional<Integer> cate, Optional<String> find) {
        List<ProductVO> productVOS =  this.getListP(cate, find);
        List<ProductVO> productVO = new ArrayList<>();
        for (ProductVO productVO1 : productVOS
        ) {
            if( productVO1.getStatus().equals("Đang bán")) {
                productVO.add(productVO1);
            } else {
                continue;
            }
        }
        return productVO;
    }
    @Override
    public List<ProductVO> getListAdmin(Optional<Integer> cate, Optional<String> find, Principal principal) {
        if (principal == null) {
            return null;
        }
        if (!checkRole.isHavePermition(principal.getName(), "Director")
                && !checkRole.isHavePermition(principal.getName(), "Staff"))
        {return null;}
        List<ProductVO> productVOS =  this.getListP(cate, find);
        List<ProductVO> productVO = new ArrayList<>();
        for (ProductVO productVO1 : productVOS
        ) {
            if(productVO1.getStatus().equals("Đã xóa") ) {
                continue;
            } else {
                productVO.add(productVO1);
            }
        }
        return productVO;
    }

    @Override
    public List<ProductVO> getListDeleteAdmin(Optional<Integer> cate, Optional<String> find, Principal principal) {
        if (principal == null) {
            return null;
        }
        if (!checkRole.isHavePermition(principal.getName(), "Director")
                && !checkRole.isHavePermition(principal.getName(), "Staff"))
        {return null;}
        List<ProductVO> productVOS =  this.getListP(cate, find);
        List<ProductVO> productVO = new ArrayList<>();
        for (ProductVO productVO1 : productVOS
        ) {
//            productVO1.getStatus().equals("Không kinh doanh") || productVO1.getStatus().equals("Ngừng kinh doanh") ||
            if( productVO1.getStatus().equals("Đã xóa") ) {
                productVO.add(productVO1);
            } else {
                continue;
            }
        }
        return productVO;
    }

    @Override
    public List<ProductVO> getByPrice(Optional<Long> start, Optional<Long> end) {
        List<ProductVO> productVOS = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        if (start.isPresent() && end.isPresent()) {
            products = productDAO.findAllByPriceBetween(start.get(), end.get());
        } else if (start.isPresent() || end.isPresent()) {
            if (start.isPresent()) {
                products = productDAO.findAllByPriceGreaterThanEqual(start.get());
            } else {
                products = productDAO.findAllByPriceLessThanEqual(end.get());
            }
        } else {
            products = productDAO.findAll();
        }
        for (Product product : products) {
            ProductVO productVO = productUtils.convertToVO(product);
            productVOS.add(productVO);
        }
        return productVOS;
    }

    @Override
    public ProductCategoryVO selectCate(Integer pid, Integer cid, Principal principal) {
        if (principal == null) {
            return null;
        }
        if (!checkRole.isHavePermition(principal.getName(), "Director")
                && !checkRole.isHavePermition(principal.getName(), "Staff"))
            return null;
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductId(pid);
        productCategory.setCategoryId(cid);
        productCategory = productCategoryDAO.save(productCategory);
        ProductCategoryVO productCategoryVO = new ProductCategoryVO();
        BeanUtils.copyProperties(productCategory, productCategoryVO);
        return productCategoryVO;
    }

    @Override
    public Boolean unSelectCate(Integer pid, Integer cid, Principal principal) {
        if (principal == null) {
            return false;
        }
        if (!checkRole.isHavePermition(principal.getName(), "Director")
                && !checkRole.isHavePermition(principal.getName(), "Staff"))
            return false;
        productCategoryDAO.unSelect(pid, cid);
        return true;
    }

    @Override
    public List<ProductVO> getTrending() {
        List<ProductVO> productVOS = new ArrayList<>();
        if (AutoTaskService.trending.size() < 8) {
            for (TrendingVO trendingVO : AutoTaskService.trending) {
                productVOS.add(trendingVO.getProductVO());
            }
        } else {
            for (int i = 0; i < 8; i++) {
                productVOS.add(AutoTaskService.trending.get(i).getProductVO());
            }
        }
        return productVOS;
    }

    @Override
    public List<ProductVO> getMostNew() {
        List<ProductVO> productVOS = new ArrayList<>();
//        for (Product  product: productDAO.
//             ) {
//
//        }
        return productVOS;
    }

    @Override
    public List<Product> getListByCate(Integer cateId) {
        List<Product> products = new ArrayList<>();
        List<ProductCategory> productCategories = productCategoryDAO.findAllByCategoryIdEquals(cateId);
        for (ProductCategory productCategory : productCategories) {
            Optional<Product> optionalProduct = productDAO.findById(productCategory.getProductId());

            List<ProductVO> productVOS = new ArrayList<>();

            if (optionalProduct.isPresent()) {
                products.add(optionalProduct.get());
            }
        }
        return products;
    }

    @Override
    public ProductVO getById(Integer id) throws NullPointerException {
        Product product = productDAO.findById(id).orElseThrow(() -> new NullPointerException("Product not found with id: " + id));
        ProductVO productVO = productUtils.convertToVO(product);
        try {
            productVO.setBlogs(getBlogByProductIdAndType(productVO.getId(), 1));
        } catch (NullPointerException e) {
            productVO.setBlogs(null);
        }
        return productVO;
    }


//    private ProductVO convertToVO(Product product) {
//        ProductVO productVO = new ProductVO();
//        BeanUtils.copyProperties(product, productVO);
//        List<ProductColorVO> productColorVOS = new ArrayList<>();
//        for (ProductColor productColor : productColorDAO.findAllByProductIdEquals(productVO.getId())) {
//            ProductColorVO productColorVO = new ProductColorVO();
//            BeanUtils.copyProperties(productColor, productColorVO);
//            productColorVOS.add(productColorVO);
//        }
//        productVO.setProductColors(productColorVOS);
//        List<ProductDetailsVO> productDetailsVOS = new ArrayList<>();
//        List<String> photos = new ArrayList<>();
//        for (ProductDetails productDetails : productDetailsDAO.findAllByProductIdEquals(productVO.getId())) {
//            ProductDetailsVO productDetailsVO = new ProductDetailsVO();
//            if (productDetails.getPropertyName().equalsIgnoreCase("photo")) {
//                for (String photo : productDetails.getPropertyValue().split(",")) {
//                    photos.add(photo.trim());
//                }
//            } else {
//                BeanUtils.copyProperties(productDetails, productDetailsVO);
//                productDetailsVOS.add(productDetailsVO);
//            }
//        }
//        productVO.setProductDetails(productDetailsVOS);
//        productVO.setPhotos(photos);
//        productVO.setDiscount(priceUtils.maxDiscountAtPresentOf(productVO.getId()));
//
//        return productVO;
//    }


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

    //admin

    @Override
    @Transactional
    public Object delete(Integer id, Principal principal) {
        if (principal == null)
            return false;
        if (checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) {
            try {
                Product product = productDAO.getById(id);
                if(product == null){
                    throw new NotFoundException("api.error.API-003");
                }
                product.setStatus("Đã xóa");
                productDAO.save(product);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
    @Override
    @Transactional
    public Object dontSell(Integer id, Principal principal) {
        if (principal == null)
            return false;
        if (checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) {
            try {
                Product product = productDAO.getById(id);
                if(product == null){
                    throw new NotFoundException("api.error.API-003");
                }
                product.setStatus("Ngừng kinh doanh");
                productDAO.save(product);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }


    @Override
    public ProductVO newProduct(ProductVO productVO, Principal principal) {
        if (principal == null) {
            return null;
        }
        if (checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) {
            Product product = new Product();
            BeanUtils.copyProperties(productVO, product);
            product.setStatus("Chưa thêm đủ thông tin");
            if(productVO.equals("Không kinh doanh")){
                product.setStatus("Không kinh doanh");
            }
            product = productDAO.save(product);

            List<ProductCategoryVO> productCategoryVO = productVO.getProductCategories();
            if(productCategoryVO == null){
                throw new NotImplementedException("Chưa thêm category");
            }
            List<ProductCategory> productCategories = new ArrayList<>();

            for (ProductCategoryVO productCategoryVO1 : productCategoryVO) {

                Category category = categoryDAO.findOneById(productCategoryVO1.getCategoryId());
                if(category == null){
                    throw new NotImplementedException("Chưa thêm category");
                }
                ProductCategory productCategory = new ProductCategory();
                productCategoryVO1.setProductId(product.getId());
                productCategoryVO1.setCategoryId(category.getId());
                BeanUtils.copyProperties(productCategoryVO1, productCategory);
                productCategories.add(productCategory);
            }
            productCategoryDAO.saveAll(productCategories);
            productVO.setId(product.getId());
            return productVO;
        }
        return null;
    }

    @Override
    public ProductVO update(ProductVO productVO, Principal principal) {
        if (principal == null) {
            return null;
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) || productVO.getId() == null) {
            return null;
        }
        if(productDAO.getOneProductById(productVO.getId()) == null){
            throw new NotFoundException("api.error.API-003");
        }
        Product product = new Product();
        BeanUtils.copyProperties(productVO, product);
        productDAO.save(product);

        List<ProductCategoryVO> productCategoryVO = productVO.getProductCategories();
        List<ProductCategory> productCategories = new ArrayList<>();
        productCategoryDAO.deleteAllByProductIdEquals(product.getId());
        for (ProductCategoryVO productCategoryVO1 : productCategoryVO) {
            ProductCategory productCategory = new ProductCategory();
            BeanUtils.copyProperties(productCategoryVO1, productCategory);
            productCategories.add(productCategory);
        }
        productCategoryDAO.saveAll(productCategories);
        return getById(product.getId());
    }

}

package com.poly.datn.dao;

import com.poly.datn.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Integer> {
    List<Product> getProductById(Integer id);

    Product getOneProductById(Integer id);

    @Query(value = "select  * from product  order by name limit 8", nativeQuery = true)
    List<Product> findTrend();


    @Query(nativeQuery = true, value = "SELECT product_id, COUNT(product_id) as 'SL'\n" +
            "FROM order_details o INNER JOIN \n" +
            "(SELECT order_id \n" +
            "FROM ordermanagement\n" +
            "where `status` = 'Giao hàng thành công'\n" +
            "ORDER BY time_change DESC) m\n" +
            "where o.order_id = m.order_id\n" +
            "GROUP BY product_id\n" +
            "ORDER BY 'SL'\n" +
            "LIMIT 100")
    List<Integer[]> getTop100ProductSell();

    @Query(nativeQuery = true,value = "select * from  product  c where c.status='Đang bán' order by  c.id desc limit 8")
    List<Product> findNewTopProduct();

    List<Product> findAllByPriceBetween(Long low, Long high);

    List<Product> findAllByPriceLessThanEqual(Long high);

    List<Product> findAllByPriceGreaterThanEqual(Long low);

    @Query(nativeQuery = true,value = "select  * from product p where p.id not in (select distinct product_id from blog b where b.type = 1)")
    List<Product> getNotContainBlog();
}

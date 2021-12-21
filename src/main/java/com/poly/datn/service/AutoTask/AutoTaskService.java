package com.poly.datn.service.AutoTask;

import com.poly.datn.dao.AccountDAO;
import com.poly.datn.dao.BlogDAO;
import com.poly.datn.dao.ProductDAO;
import com.poly.datn.dao.SaleDAO;
import com.poly.datn.entity.Account;
import com.poly.datn.entity.Blog;
import com.poly.datn.entity.Product;
import com.poly.datn.entity.Sale;
import com.poly.datn.service.impl.SendMail;
import com.poly.datn.utils.ProductUtils;
import com.poly.datn.vo.ProductVO;
import com.poly.datn.vo.TrendingVO;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class AutoTaskService {
    @Autowired
    ProductDAO productDAO;

    static Map<Integer, Boolean> sendBlog = new HashMap<>();
    static Map<String, String> userDetail = new HashMap<>();
    public static List<TrendingVO> trending = new ArrayList<>();
    public static LocalDateTime nearestEndSaleTime;
    public static LocalDateTime nearesrStartSaleTime;

    @Autowired
    BlogDAO blogDAO;

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    SaleDAO saleDAO;

    @Autowired
    SendMail sendMail;

    @Autowired
    ProductUtils productUtils;


    @Scheduled(cron = "0 30 0/1 ? * * ")
//    @Scheduled(fixedRate = 3000)
    @EventListener(ApplicationReadyEvent.class)
    protected void scanUserDetail() {
        List<Account> accounts = accountDAO.findAll();
        for (Account account : accounts) {
            if (!userDetail.containsKey(account.getEmail())) {
                userDetail.put(account.getEmail(), account.getFullname());
            }

        }
    }

    @Scheduled(cron = "0 45 0/1 ? * * ")
//    @Scheduled(fixedRate = 6000)
    @EventListener(ApplicationReadyEvent.class)
    protected void scanBlog() {
        Timestamp end = Timestamp.valueOf(LocalDateTime.now());
        Timestamp start = Timestamp.valueOf(LocalDateTime.now().minusMinutes(45));
        for (Map.Entry<Integer, Boolean> entry : sendBlog.entrySet()) {
            if (entry.getValue()) {
                sendBlog.remove(entry.getKey());
            }
        }
        List<Blog> blogs = blogDAO.findAllByTimeBetween(start, end);
        for (Blog blog : blogs) {
            if (blog.getType().equals(1)) {
                continue;
            }
            if (!blog.getStatus()) {
                continue;
            }
            if (sendBlog.containsKey(blog.getId())) {
                if (sendBlog.get(blog.getId())) {
                    sendBlog.remove(blog.getId());
                }
            } else {
                sendBlog.put(blog.getId(), false);
            }
        }
    }
    @Scheduled(cron = "0 0 0/1 1/1 * ? ")
//    @Scheduled(fixedRate = 20000)
    @EventListener(ApplicationReadyEvent.class)
    protected void add2DBJob() throws ParseException {
        for (Map.Entry<Integer, Boolean> entry : sendBlog.entrySet()) {
            Blog blog = blogDAO.findOneById(entry.getKey());
            Account account = accountDAO.getById(blog.getCreatedBy());
            blog.setAccount(account);
            try {
                sendMail.sentBlogMail(userDetail, blog);
            } catch (MessagingException | IOException | TemplateException ex) {
            }
            sendBlog.replace(blog.getId(), true);
        }
    }

    //    @Scheduled(fixedRate = 500000)
    @Scheduled(cron = "0 0 0/1 1/1 * ? ")
    @EventListener(ApplicationReadyEvent.class)
    protected void getTrending() {
        trending.clear();
        for (Integer[] ls : productDAO.getTop100ProductSell()) {
            Integer id = ls[0];
            Product product = productDAO.getOneProductById(id);
            ProductVO productVO = productUtils.convertToVO(product);
            TrendingVO trendingVO = new TrendingVO(productVO, ls[1]);
            trending.add(trendingVO);
        }
        Collections.sort(trending, Comparator.comparingInt(TrendingVO::getQuantity).reversed());
    }

    @Scheduled(cron = "* * * * * ?")
    @EventListener(ApplicationReadyEvent.class)
    void changeSaleStatus() {
        LocalDateTime thisTime = LocalDateTime.now();
        if (thisTime.equals(AutoTaskService.nearesrStartSaleTime) ||
                thisTime.equals(AutoTaskService.nearesrStartSaleTime)) {
            Timestamp nearestStart = saleDAO.getNearestTimeStartSale();
            Timestamp nearestEnd = saleDAO.getNearestTimeEndSale();

            if (thisTime.equals(AutoTaskService.nearesrStartSaleTime)) {
                List<Sale> endSale = saleDAO.getAllByEndTimeEquals(Timestamp.valueOf(thisTime));
                for (Sale sale : endSale) {
                    sale.setStatus("Đã kết thúc");
                    saleDAO.save(sale);
                }
            }
            if (thisTime.equals(AutoTaskService.nearesrStartSaleTime)) {
                List<Sale> startSale = saleDAO.getAllByStartTimeEquals(Timestamp.valueOf(thisTime));
                for (Sale sale : startSale) {
                    sale.setStatus("Đang diễn ra");
                    saleDAO.save(sale);
                }
            }
            AutoTaskService.nearesrStartSaleTime = nearestStart.toLocalDateTime();
            AutoTaskService.nearestEndSaleTime = nearestEnd.toLocalDateTime();
        }
    }

    @Scheduled(cron = "0 0 * * * ?")
    @EventListener(ApplicationReadyEvent.class)
    void updateSale() {
        List<Sale> salesStarted = saleDAO.getStartedSaleButNotChangeStatus();
        List<Sale> salesEnded = saleDAO.getExpSaleButNotChangeStatus();
        for (Sale sale : salesStarted) {
            sale.setStatus("Đang diễn ra");
            saleDAO.save(sale);
        }
        for (Sale sale : salesEnded) {
            sale.setStatus("Đã kết thúc");
            saleDAO.save(sale);
        }
    }
}

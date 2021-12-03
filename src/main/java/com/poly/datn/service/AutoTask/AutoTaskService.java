package com.poly.datn.service.AutoTask;

import com.poly.datn.dao.AccountDAO;
import com.poly.datn.dao.BlogDAO;
import com.poly.datn.entity.Account;
import com.poly.datn.entity.Blog;
import com.poly.datn.service.impl.SendMail;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AutoTaskService {

    private Map<Integer, Boolean> sendBlog = new HashMap<>();
    private Map<String, String> userDetail = new HashMap<>();

    @Autowired
    BlogDAO blogDAO;

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    SendMail sendMail;

    @Scheduled(cron = "0 30 0/1 ? * * *")
    private void scanUserDetail() {
        for (Account account : accountDAO.findAll()) {
            if (!userDetail.containsKey(account.getEmail()))
                userDetail.put(account.getEmail(), account.getFullname());
        }
    }

    @Scheduled(cron = "0 45 0/1 ? * * *")
    private void scanBlog() {
        Timestamp end = Timestamp.valueOf(LocalDateTime.now());
        Timestamp start = Timestamp.valueOf(LocalDateTime.now().minusMinutes(45));
        for (Map.Entry<Integer, Boolean> entry : sendBlog.entrySet()) {
            if (entry.getValue()) {
                sendBlog.remove(entry.getKey());
            }
        }
        for (Blog blog : blogDAO.findAllByTimeCreatedBetween(start, end)) {
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


    @Scheduled(cron = "0 0 0/1 1/1 * ? *")
    public void add2DBJob() throws ParseException {
        for (Map.Entry<Integer, Boolean> entry : sendBlog.entrySet()) {
            Blog blog = blogDAO.getById(entry.getKey());
            try {
                sendMail.sentBlogMail(userDetail, blog);
            } catch (MessagingException | IOException | TemplateException ex) {
            }
            sendBlog.replace(blog.getId(), true);
        }
    }
}

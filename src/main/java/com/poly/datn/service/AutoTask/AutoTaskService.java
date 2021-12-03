package com.poly.datn.service.AutoTask;

import com.poly.datn.dao.AccountDAO;
import com.poly.datn.dao.BlogDAO;
import com.poly.datn.entity.Account;
import com.poly.datn.entity.Blog;
import com.poly.datn.vo.mailSender.InfoSendBlog;
import org.jetbrains.annotations.Async;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AutoTaskService {
    @Autowired
    BlogDAO blogDAO;

    @Autowired
    AccountDAO accountDAO;

    @Scheduled(cron ="0 0 0/1 1/1 * ? *")
    public void add2DBJob() throws ParseException {
        List<Blog> blogs = blogDAO.findAllInHour();
        List<Account> accounts = accountDAO.findAllEmail();


        List<InfoSendBlog> infoSendBlogs = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> emailAcc = new ArrayList<>();

        for (Blog blog : blogs) {
            if (blog.getStatus() == true) {
                if (blog.getBlogDetails() == null) {
                    if(blog.getType() == 2)
                    for (Account account : accounts) {
                        name.add(account.getFullname());
                        emailAcc.add(account.getEmail());
                    }

                }

            }
        }

    }
}

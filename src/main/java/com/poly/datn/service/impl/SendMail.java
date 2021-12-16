package com.poly.datn.service.impl;

import com.poly.datn.dao.CustomerDAO;
import com.poly.datn.entity.Blog;
import com.poly.datn.vo.mailSender.InfoSendBlog;
import com.poly.datn.vo.mailSender.InfoSendMailRPass;
import com.poly.datn.vo.mailSender.InfoSendOrder;
import freemarker.core.ParseException;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SendMail {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private Configuration config;

    @Autowired
    private CustomerDAO customerDAO;
    //quên pass
    public void sentResetPasswordMail(String email, String resetLink, String name) throws MessagingException, IOException, TemplateException {
        InfoSendMailRPass infoSendMail = new InfoSendMailRPass();
        infoSendMail.setName(name);
        infoSendMail.setResetLink(resetLink);
        String mailSubject = "Quên mật khẩu";

        Template t = config.getTemplate("mailRPass.ftl");
        String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(t, infoSendMail);
//                "<p><b>Xin chào"+ name+"</b></b> </p>"
//                + "<p>Bạn đã gửi một yêu cầu thay đổi mật khẩu. </p>"
//                + "<p>Nhấn vào đường dẫn bên dưới để thực hiện thay đổi mật khẩu: </p>"
//                + "<p><a href=\"" + resetLink + "\">Đổi mật khẩu của bạn</a></b> </p>"
//                + "<p>Bỏ qua email này nếu bạn đã nhớ mật khẩu của mình hoặc bạn không thực hiện yêu cầu</p>"
//                + "<p>Link đổi mật khẩu này sẽ hết hạn sau 15 phút </b></b></p>"
//                +"<p><b>Trân trọng </b> </b></p>"
//                + "----------------------------------------------------------------------------------</b> </b>"
//                + "<img src='cid:logoImage'/>";

        sendMail(mailContent, mailSubject, email);
    }

    public void sentBlogMail(Map<String, String> account, Blog blog) throws MessagingException, IOException, TemplateException {
        InfoSendBlog infoSendBlog = new InfoSendBlog();
        infoSendBlog.setCreatedBy(blog.getCreatedBy());
        infoSendBlog.setPhoto(blog.getPhoto());
        infoSendBlog.setShortText(blog.getShortText());
        infoSendBlog.setTitle(blog.getTitle());
        infoSendBlog.setTimeCreated(sdf.format(Date.from(blog.getTimeCreated().toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant())));
        Template template = config.getTemplate("mailNewBlog.ftl");

        for (Map.Entry<String, String> entry : account.entrySet()) {
            infoSendBlog.setName(entry.getValue());
            infoSendBlog.setEmail(entry.getKey());
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, infoSendBlog);
            sendMail(content, "Blog Mới từ socstore", entry.getKey());
        }
//
//        String mailSubject = infoSendBlog.getTitle();
//        Template t = config.getTemplate("mailRPass.ftl");
//
//        String email = InfoSendBlog.getEmail();
////        String EmailAcc;
////            for(EmailAcc email1 : email){
//        String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(t, infoSendBlogs);
////                "<p><b>Xin chào"+ name+"</b></b> </p>"
////                + "<p>Bạn đã gửi một yêu cầu thay đổi mật khẩu. </p>"
////                + "<p>Nhấn vào đường dẫn bên dưới để thực hiện thay đổi mật khẩu: </p>"
////                + "<p><a href=\"" + resetLink + "\">Đổi mật khẩu của bạn</a></b> </p>"
////                + "<p>Bỏ qua email này nếu bạn đã nhớ mật khẩu của mình hoặc bạn không thực hiện yêu cầu</p>"
////                + "<p>Link đổi mật khẩu này sẽ hết hạn sau 15 phút </b></b></p>"
////                +"<p><b>Trân trọng </b> </b></p>"
////                + "----------------------------------------------------------------------------------</b> </b>"
////                + "<img src='cid:logoImage'/>";
//
//        sendMail(mailContent, mailSubject, email);

    }
//    }

    public void sentMailOrder(InfoSendOrder infoSendOrder) {
        try {

            String homeLink = "http://150.95.105.29/";
            String mailSubject = "Đặt hàng thành công Socstore";
//        Customer customer = customerDAO.findCustomerById(infoSendOrder.orders.getCustomerId());

            Template t = config.getTemplate("mailOrder.ftl");
            String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(t, infoSendOrder);



            sendMail(mailContent, mailSubject, infoSendOrder.getEmail());
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sentMailRegister(String email, String name) throws MessagingException, UnsupportedEncodingException {
        String homeLink = "http://150.95.105.29/";
        String mailSubject = "Tạo tài khoản thành công Socstore";
        String mailContent = "<p><b>Hello " + name + "</b> </p>"
                + "<p>Bạn đã tạo tài khoản thành công trên hệ thống của Socstore</p>"
                + "<p>Hãy thường xuyên kiểm tra email để nhận những tin công nghệ mới nhất nhé</p>"
                + "<p><a href=\"" + homeLink + "\">Nhấn vào đây để đăng nhập ngay</a></b> </b> </p>"
                + "<p><b>Trân trọng </b> </b></p>"
                + "----------------------------------------------------------------------------------</b> </b>"
                + "<img src='cid:logoImage'/>";
        sendMail(mailContent, mailSubject, email);
    }

    private void sendMail(String content, String subject, String email) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("socstorehanoi@gmail.com", "SOC Store");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(content, true);
        ClassPathResource resource = new ClassPathResource("/static/logoshop.png");
        helper.addInline("logoImage", resource);
        javaMailSender.send(message);
    }

    private void sendMail(String content, String subject, List<String> mails) throws MessagingException, UnsupportedEncodingException {
        for (String mail : mails) {
            try {
                sendMail(content, subject, mail);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void sentMailOrderPayOk(InfoSendOrder infoSendOrder) {
        try {

            String homeLink = "http://150.95.105.29/";
            String mailSubject = "Đặt hàng thành công Socstore";

            Template t = config.getTemplate("mailOrderPayOk.ftl");
            String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(t, infoSendOrder);

            sendMail(mailContent, mailSubject, infoSendOrder.getEmail());
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

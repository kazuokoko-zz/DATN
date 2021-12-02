package com.poly.datn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class SendMail {

    @Autowired
    JavaMailSender javaMailSender;

    //quên pass
    public void sentResetPasswordMail(String email, String resetLink, String name) throws MessagingException, UnsupportedEncodingException {

        String mailSubject = "Quên mật khẩu Socstore";

        String mailContent = "<p><b>Xin chào"+ name+"</b></b> </p>"
                + "<p>Bạn đã gửi một yêu cầu thay đổi mật khẩu. </p>"
                + "<p>Nhấn vào đường dẫn bên dưới để thực hiện thay đổi mật khẩu: </p>"
                + "<p><a href=\"" + resetLink + "\">Đổi mật khẩu của bạn</a></b> </p>"
                + "<p>Bỏ qua email này nếu bạn đã nhớ mật khẩu của mình hoặc bạn không thực hiện yêu cầu</p>"
                + "<p>Link đổi mật khẩu này sẽ hết hạn sau 15 phút </b></b></p>"
                +"<p><b>Trân trọng </b> </b></p>"
                + "----------------------------------------------------------------------------------</b> </b>"
                + "<img src='cid:logoImage'/>";

        sendMail(mailContent, mailSubject, email);
    }
    public void sentMailRegister(String email, String name) throws MessagingException, UnsupportedEncodingException {
        String homeLink = "http://150.95.105.29/";
        String mailSubject = "Tạo tài khoản thành công Socstore";
        String mailContent = "<p><b>Hello "+ name+"</b> </p>"
                + "<p>Bạn đã tạo tài khoản thành công trên hệ thống của Socstore</p>"
                + "<p>Hãy thường xuyên kiểm tra email để nhận những tin công nghệ mới nhất nhé</p>"
                + "<p><a href=\"" + homeLink + "\">Nhấn vào đây để đăng nhập ngay</a></b> </b> </p>"
                +"<p><b>Trân trọng </b> </b></p>"
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
            } catch (Exception e){
                throw new RuntimeException(e);
            }

        }
    }
}

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
    public void sentResetPasswordMail(String email, String resetLink) throws MessagingException, UnsupportedEncodingException {


        String mailSubject = "Reset password account";

        String mailContent = "<p><b>Hello</b> </p>"
                + "<p>You have requested to reset password. </p>"
                + "<p>Click the link below to change your password: </p>"
                + "<p><a href=\"" + resetLink + "\">Change you password</a></b> </p>"
                + "<p>Ignore this email if you do remember your password, or you have not made the request </p>"
                + "<p>Link đổi mật khẩu này sẽ hết hạn sau 15 phút </p>"
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
            sendMail(content, subject, mail);
        }
    }
}

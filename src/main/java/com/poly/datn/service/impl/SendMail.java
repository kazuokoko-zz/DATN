package com.poly.datn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class SendMail {

    @Autowired
    JavaMailSender javaMailSender;

    //quên pass
    public void sentMail(String email, String resetLink) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);


        String mailSubject = "Reset password account";

        String mailContent = "<p><b>Hello</b> </p>"
                            + "<p>You have requested to reset password. </p>"
                            + "<p>Click the link below to change your password: </p>"
                            + "<p><a href=\""  + resetLink + "\">Change you password</a></b> </p>"
                            + "<p>Ignore this email if you do remember your password, or you have not made the request </p>"
                            + "<img src='cid:logoImage'/>";

        helper.setFrom("socstorehanoi@gmail.com", "SOC Store");
        helper.setTo(email);

        helper.setSubject(mailSubject);
        helper.setText(mailContent, true);
        ClassPathResource resource = new ClassPathResource("/static/logoshop.png");
        helper.addInline("logoImage", resource);
        javaMailSender.send(message);
     }
}

package com.example.demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    public void sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("cruzazulanimales@gmail.com");
        message.setTo("cruzazulanimales@gmail.com");
        message.setSubject("Prueba envio email simple");
        message.setText("Esto es el contenido del Email");

        javaMailSender.send(message);
    }

    public void sendEmailTemplate(){

        MimeMessage message = javaMailSender.createMimeMessage();
        try {

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Context context = new Context();
            String htmlText = templateEngine.process("email_template", context);

            helper.setFrom("cruzazulanimales@gmail.com");
            helper.setTo("cruzazulanimales@gmail.com");
            helper.setSubject("Prueba envio email simple");
            helper.setText(htmlText,true);

            javaMailSender.send(message);

        } catch (MessagingException e){
            e.printStackTrace();
        }
    }
}

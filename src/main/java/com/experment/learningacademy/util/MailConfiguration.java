package com.experment.learningacademy.util;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {
    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("jahidhasansaif095@gmail.com");
        mailSender.setPassword("wdqu fgzg vask wqbn");
        Properties prop=mailSender.getJavaMailProperties();
        prop.put("mail.transport.protocol","smtp");
        prop.put("mail.smtp.auth","true");
        prop.put("mail.smtp.starttls.enable","true");
        prop.put("mail.smtp.ssl.protocols","TLSv1.2");
        prop.put("mail.smtp.ssl.trust","smtp.gmail.com");

        return mailSender;


    }
}

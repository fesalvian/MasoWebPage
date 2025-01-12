package com.MasoWebPage.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String remetente;



    public void enviarEmail(String destinatario, String assunto, String mensagem){
        try{
            System.out.println("entrando no try");

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(remetente);
            simpleMailMessage.setTo(destinatario);
            simpleMailMessage.setSubject(assunto);
            simpleMailMessage.setText(mensagem);
            javaMailSender.send(simpleMailMessage);
        }catch (RuntimeException e){
            System.out.println("Erro ao enviar email " + e.getLocalizedMessage());
            System.out.println(e);
        }
    }
}

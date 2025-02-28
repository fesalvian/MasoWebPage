package com.MasoWebPage.backend.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {


    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String remetente;

    @Value("${link_validacao_email}")
    private String linkValidacao;



    public void enviarEmail(String destinatario, String assunto, String msg){
        try{
            MimeMessage mensagem = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setFrom(remetente);
            helper.setText(msg, true); // Define o e-mail como HTML

            mailSender.send(mensagem);


        }catch (RuntimeException e){
            System.out.println("Erro ao enviar email " + e.getLocalizedMessage());
            System.out.println(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
package com.MasoWebPage.backend.services.emailValidacao;

import com.MasoWebPage.backend.api.dto.lead.LeadDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Service
public class EmailValidacaoService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String remetente;

    @Value("${link_validacao_email}")

    private String linkValidacao;

    @Autowired
    private ObjectMapper objectMapper;
    public void enviarEmailDeValidacao(String tokenDeValidacao, LeadDTO lead) {

        try {
            // Cria a mensagem de email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Define o destinatário, assunto e corpo do email
            helper.setTo(lead.email());
            helper.setSubject("Valide seu email");

            // Cria o link de validação
            String linkEToken = linkValidacao+ tokenDeValidacao;
            String htmlTemplate = toStringHtml().replace("LINK_DE_VALIDACAO" , linkEToken)
                    .replace("LEAD_DADOS", objectMapper.writeValueAsString(lead));

            helper.setText(htmlTemplate, true);
            mailSender.send(message);

            System.out.println("link validacao " + linkEToken);
            System.out.println(htmlTemplate);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String toStringHtml(){
        String pathHtml = "C:\\Users\\ferna\\OneDrive\\Documentos\\" +
                "a1-PROJETOS\\fatecie\\projeto-semeste-4\\MasoWebPage\\" +
                "backend\\src\\main\\java\\com\\MasoWebPage\\backend\\services\\emailValidacao"+
                "\\validacaoEmail.html";
        String htmlString = "";
        File file = new File(pathHtml);
        Scanner scanner;
        try {
             scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNext()){
            htmlString = htmlString + scanner.nextLine() + "\n";
        }
        return htmlString;
    }
}

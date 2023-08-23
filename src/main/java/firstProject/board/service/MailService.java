package firstProject.board.service;

import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private String authNum;

    public void createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(3);
            switch (index) {
                case 0 :
                    key.append((char)(random.nextInt(26)+97));
                    break;
                case 1 :
                    key.append((char)(random.nextInt(26)+65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        authNum = key.toString();
    }

    public MimeMessage createMessage(String email) throws Exception {
        createCode();
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("이메일 인증 코드입니다");
        message.setText(setContext(authNum),"utf-8","html");
        message.setFrom("pcb7893@naver.com");
        return message;
    }

    public String setContext(String code) {
        Context context = new Context();
        context.setVariable("code" , code);
        return templateEngine.process("mail", context);
    }

    public String sendEmail(String email) throws Exception {
        MimeMessage emailForm = createMessage(email);
        emailSender.send(emailForm);
        return authNum;
    }

}

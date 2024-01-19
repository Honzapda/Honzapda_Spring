package Honzapda.Honzapda_server.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public void sendTempPassword(String toEmail, String temporaryPassword) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("HONZAPDA : 임시 비밀번호 안내");
        message.setText("안녕하세요. HONZAPDA입니다.\n회원님의 임시 비밀번호 : " + temporaryPassword
                + "\n저희 서비스를 이용해주셔서 감사합니다.");

        javaMailSender.send(message);
    }
}

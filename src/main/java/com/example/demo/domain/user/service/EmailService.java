package com.example.demo.domain.user.service;

import com.example.demo.domain.user.facade.UserFacade;
import com.example.demo.domain.user.web.dto.response.EmailCodeCheckResponsesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final UserFacade userFacade;
    private String saveEmail;

    public static final String ePw = createKey();

    private MimeMessage createMessage(String email) throws Exception {
        return message(email, "회원가입");
    }

    private MimeMessage createWithdrawalMessage(String email) throws Exception {
        return message(email, "회원탈퇴");
    }

    private MimeMessage createForgetPasswordMessage(String email) throws MessagingException, UnsupportedEncodingException, UnsupportedEncodingException {
        return message(email, "비밀번호 변경");
    }

    private static String createKey() {
        return Provider.getRandomString();
    }

    public void sendMessage(String email) throws Exception {
        MimeMessage message = createMessage(email);
        try {
            emailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    // 회원 탈퇴 이메일 전송
    public void sendWithdrawalMessage() throws Exception {
        MimeMessage message = createWithdrawalMessage(userFacade.getCurrentUser().getEmail());
        try {
            emailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public void sendForgetPassword(String email) throws Exception {
        userFacade.getCurrentUser();
        MimeMessage message = createForgetPasswordMessage(email);
        try {
            emailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        saveEmail = email;
    }

    public boolean verifyCode(String code) {
        return !ePw.equals(code);
    }

    public EmailCodeCheckResponsesDto confirmCode(String code) {
        if (verifyCode(code)) {
            return new EmailCodeCheckResponsesDto(false);
        }
        return new EmailCodeCheckResponsesDto(true);
    }

    private MimeMessage message(String email, String keyword) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, email); // 보내는 대상
        message.setSubject("[Re:Code] Confirm " + email + " to sign up"); // 제목

        String msg = Provider.contentHtml(email, keyword);
        message.setText(msg, "utf-8", "html"); // 내용
        message.setFrom(new InternetAddress("rltgjqmduftlagl@gmail.com", "Re:Code"));// 보내는 사람

        return message;
    }
}
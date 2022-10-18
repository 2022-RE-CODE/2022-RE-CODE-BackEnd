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
import java.util.Random;

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
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) {
            int index = rnd.nextInt(3);

            switch (index) {
                case 0:
                    key.append((char) (rnd.nextInt(26)) + 97);
                    break;
                case 1:
                    key.append((char) (rnd.nextInt(26) + 65));
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }

        return key.toString();
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

        String msgg = "";
        msgg += "<h2 style='color:#0068ff;'><strong> Re:Code 회원가입을 위한 이메일 주소를 확인해주세요. </strong></h2>";
        msgg += "<span style='font-size:17px ;'>반가워요! " + email
                + "</span><span style='font-size:17px';>을 통한"
                + " <strong>Re:Code</strong></span>"
                + "<span style='font-size:17px';>의 요청을 확인 중입니다.</span>" +
                "<p style='font-size:17px ;'>인증 절차가 성공적으로 이루어지면</p>" + keyword + "을 빠르게 도와드릴게요.";
        msgg += "<p style='font-size:17px ;'><strong>인증 코드를 웹사이트에 동일하게 작성해주세요: </strong></p>";
        msgg += "<div align='center' font-family:verdana';>";
        msgg += "<div style='font-size:200%'><strong>";
        msgg += ePw + "</strong><div><br/> ";
        msgg += "</div>";
        msgg += "<div align='left'><p style='font-size:17px';><strong>인증을 진행한 적이 없으시다면?</strong></p>";
        msgg += "<p style='font-size:17px';>걱정하지 마세요! 이메일 주소가 타인에 의해 잘못 기입된 것으로 보입니다.</p>";
        msgg += "<p style='font-size:17px';>타인에게 인증 코드를 알려주지 않도록, 해당 이메일을 무시하거나 삭제하셔도 됩니다.</p></div>";
        message.setText(msgg, "utf-8", "html"); // 내용
        message.setFrom(new InternetAddress("rltgjqmduftlagl@gmail.com", "Re:Code"));// 보내는 사람

        msgg += "<div style='margin:100px;'>";
        return message;
    }
}
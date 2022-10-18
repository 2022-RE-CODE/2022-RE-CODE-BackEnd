package com.example.demo.domain.user.service;

import java.util.Random;

import static com.example.demo.domain.user.service.EmailService.ePw;

public class Provider {
    public static String getRandomString() {
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

    public static String contentHtml(String email, String keyword) {
        String msg = "";
        msg += "<h2 style='color:#0068ff;'><strong> Re:Code 회원가입을 위한 이메일 주소를 확인해주세요. </strong></h2>";
        msg += "<span style='font-size:17px ;'>반가워요! " + email
                + "</span><span style='font-size:17px';>을 통한"
                + " <strong>Re:Code</strong></span>"
                + "<span style='font-size:17px';>의 요청을 확인 중입니다.</span>" +
                "<p style='font-size:17px ;'>인증 절차가 성공적으로 이루어지면</p>" + keyword + "을 빠르게 도와드릴게요.";
        msg += "<p style='font-size:17px ;'><strong>인증 코드를 웹사이트에 동일하게 작성해주세요: </strong></p>";
        msg += "<div align='center' font-family:verdana';>";
        msg += "<div style='font-size:200%'><strong>";
        msg += ePw + "</strong><div><br/> ";
        msg += "</div>";
        msg += "<div align='left'><p style='font-size:17px';><strong>인증을 진행한 적이 없으시다면?</strong></p>";
        msg += "<p style='font-size:17px';>걱정하지 마세요! 이메일 주소가 타인에 의해 잘못 기입된 것으로 보입니다.</p>";
        msg += "<p style='font-size:17px';>타인에게 인증 코드를 알려주지 않도록, 해당 이메일을 무시하거나 삭제하셔도 됩니다.</p></div>";

        return msg;
    }
}

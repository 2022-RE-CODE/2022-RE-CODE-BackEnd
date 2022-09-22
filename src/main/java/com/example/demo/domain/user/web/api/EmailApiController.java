package com.example.demo.domain.user.web.api;

import com.example.demo.domain.user.service.EmailService;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.domain.user.web.dto.response.EmailCodeCheckResponsesDto;
import com.example.demo.global.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/email")
@RestController
public class EmailApiController {

    private final EmailService emailService;
    private final UserService userService;

    @PostMapping("/join")
    public String join(@RequestParam String email) throws Exception {
        emailService.sendSimpleMessage(email);
        return "코드 발송 완료!\n" + email + "에서 메일을 확인해주세요.";
    }

    @PostMapping("/password")
    public String confirmForgetPasswordEmailSender(@RequestParam String email) throws Exception {
        emailService.sendForgetPassword(email);
        userService.setEmail(email);
        return "코드 발송 완료!\n" + email + "에서 메일을 확인해주세요.";
    }

    @PostMapping("/checkedCode")
    public EmailCodeCheckResponsesDto checkedCode(@RequestParam String code) {
        // return true: 다음 화면
        return emailService.confirmCode(code);
    }

    @PostMapping("/delete")
    public String confirmDeleteEmailSender() throws Exception {
        String myAccount = SecurityUtil.getLoginUserEmail();
        emailService.sendWithdrawalMessage(myAccount);
        return "코드 발송 완료!\n" + myAccount + "에서 메일을 확인해주세요.";
    }
}

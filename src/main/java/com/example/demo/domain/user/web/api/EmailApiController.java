package com.example.demo.domain.user.web.api;

import com.example.demo.domain.user.service.EmailService;
import com.example.demo.domain.user.web.dto.response.EmailCodeCheckResponsesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/email")
@RestController
public class EmailApiController {

    private final EmailService emailService;

    @PostMapping("/join")
    public void join(@RequestParam String email) throws Exception {
        emailService.sendMessage(email);
    }

    @PostMapping("/password")
    public void confirmForgetPasswordEmailSender(@RequestParam String email) throws Exception {
        emailService.sendForgetPassword(email);
    }

    @PostMapping("/checkedCode")
    public EmailCodeCheckResponsesDto checkedCode(@RequestParam String code) {
        // return true: 다음 화면
        return emailService.confirmCode(code);
    }

    @PostMapping("/delete")
    public void confirmDeleteEmailSender() throws Exception {
        emailService.sendWithdrawalMessage();
    }
}

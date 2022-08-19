package com.example.demo.domain.user.web.api;

import com.example.demo.domain.user.service.EmailService;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.domain.user.web.dto.EmailDto;
import com.example.demo.domain.user.web.dto.request.EmailCodeCheckRequestDto;
import com.example.demo.domain.user.web.dto.response.EmailCodeCheckResponsesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/mail")
@RestController
public class EmailApiController {

    private final EmailService emailService;
    private final UserService userService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public String join(@RequestBody @Valid EmailDto request) throws Exception {
        emailService.sendSimpleMessage(request.getEmail());
        return "코드 발송 완료!\n" + request.getEmail() + "에서 메일을 확인해주세요.";
    }

    @PostMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public String confirmForgetPasswordEmailSender(@RequestBody @Valid EmailDto request) throws Exception {
        emailService.sendForgetPassword(request.getEmail());
        userService.setEmail(request.getEmail());
        return "코드 발송 완료!\n" + request.getEmail() + "에서 메일을 확인해주세요.";
    }

    @PostMapping("/checkedCode")
    @ResponseStatus(HttpStatus.OK)
    public EmailCodeCheckResponsesDto checkedCode(@RequestBody @Valid EmailCodeCheckRequestDto request) {
        // return true: 다음 화면
        return emailService.confirmCode(request.getCode());
    }
}

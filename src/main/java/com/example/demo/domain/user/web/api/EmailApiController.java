package com.example.demo.domain.user.web.api;

import com.example.demo.domain.user.service.EmailService;
import com.example.demo.domain.user.web.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/mail")
@RestController
public class EmailApiController {

    private final EmailService emailService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public String join(@RequestBody @Valid EmailDto request) throws Exception {
        emailService.sendSimpleMessage(request.getEmail());
        return "코드 발송 완료!\n" + request.getEmail() + "에서 메일을 확인해주세요.";
    }

}

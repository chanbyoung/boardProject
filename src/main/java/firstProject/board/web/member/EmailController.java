package firstProject.board.web.member;

import firstProject.board.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailController {
    private final MailService mailService;

    @PostMapping("/mailConfirm")
    public void mailConfirm(@RequestParam String email) throws Exception {
        String authCode = mailService.sendEmail(email);
        log.info("인증코드 = {} ", authCode);
    }
}

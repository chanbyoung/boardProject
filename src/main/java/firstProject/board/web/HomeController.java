package firstProject.board.web;

import firstProject.board.domain.member.Member;
import firstProject.board.repository.member.SpringDataJpaMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {
    private final SpringDataJpaMemberRepository memberRepository;

    @GetMapping("/")
    public String homeLogin(Principal principal, Model model ){
        if(principal == null){
            return "home";
        }
        Member member = memberRepository.findByLoginId(principal.getName());
        model.addAttribute("member", member);
        return "loginHome";
    }
}

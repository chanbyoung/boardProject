package firstProject.board.web.member;

import firstProject.board.domain.member.Gender;
import firstProject.board.domain.member.Member;
import firstProject.board.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member) {
        return "members/addMemberForm";
    }

    /**
     * enum
     */
    @ModelAttribute("genders")
    public Gender[] genders() {
        return Gender.values();
    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }
        memberRepository.save(member);
        return "redirect:/";
    }

    @GetMapping("/{memberId}")
    public String member(@PathVariable("memberId") Long id, Model model) {
        Member member = memberRepository.findById(id);
        model.addAttribute("member", member);
        return "members/member";
    }
}

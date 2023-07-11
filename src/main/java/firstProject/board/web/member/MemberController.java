package firstProject.board.web.member;

import firstProject.board.SessionConst;
import firstProject.board.domain.member.Gender;
import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Comment;
import firstProject.board.domain.post.Post;
import firstProject.board.repository.member.MemberAddDto;
import firstProject.board.repository.member.MemberRepository;
import firstProject.board.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/add")
    public String addForm(Model model)
    {
        model.addAttribute("memberAddDto", new MemberAddDto());
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
    public String save(@Validated @ModelAttribute MemberAddDto memberAddDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "members/addMemberForm";
        }
        memberService.saveMember(memberAddDto);
        return "redirect:/";
    }

    @GetMapping("/{memberId}")
    public String member(@PathVariable("memberId") Long id, Model model) {
        Member member = memberRepository.findById(id);
        model.addAttribute("member", member);
        List<Post> posts = member.getPosts();
        List<Comment> comments = member.getComments();
        model.addAttribute("posts", posts);
        model.addAttribute("comments", comments);
        return "members/member";
    }

    @GetMapping("/{memberId}/delete")
    public String deleteMember(@PathVariable("memberId") Long id, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, HttpServletRequest request) {
        if(memberService.equalMember(loginMember,id)) {
            memberService.deleteMember(id);
            HttpSession session = request.getSession();
            if (session != null) {
                session.invalidate();
            }
        }return "redirect:/";}
}

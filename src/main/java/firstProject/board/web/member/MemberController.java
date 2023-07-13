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
import java.util.Optional;

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
        Optional<Member> findLoginId = memberRepository.findByLoginId(memberAddDto.getLoginId());
        if (!findLoginId.isEmpty()) {
            bindingResult.reject("loginId", "아이디가 중복되었습니다");
        }
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

    @GetMapping("/{memberId}/find")
    public String findMember(@PathVariable("memberId") Long id,@RequestParam("username") String username, Model model) {
        log.info("username = {} ", username);
        log.info("memberId = {} ", id);
        List<Member> members = memberRepository.findByName(username);
        log.info("members={} ", members);
        model.addAttribute("members", members);
        model.addAttribute("id", id);
        return "members/members";
    }
}

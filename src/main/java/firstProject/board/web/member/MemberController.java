package firstProject.board.web.member;

import firstProject.board.domain.member.Gender;
import firstProject.board.domain.member.Member;
import firstProject.board.repository.member.MemberRepository;
import firstProject.board.repository.member.SpringDataJpaMemberRepository;
import firstProject.board.repository.member.dto.MemberAddDto;
import firstProject.board.repository.member.dto.MemberGetDto;
import firstProject.board.repository.member.dto.MemberUpdateDto;
import firstProject.board.repository.member.dto.find.MemberFindLoginIdDto;
import firstProject.board.repository.member.dto.find.MemberFindPasswordDto;
import firstProject.board.repository.post.dto.CommentDto;
import firstProject.board.repository.post.dto.PostsGetDto;
import firstProject.board.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {
    private final MemberRepository memberRepository;
    private final SpringDataJpaMemberRepository jpaMemberRepository;
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
        Optional<Member> email = memberService.findEmail(memberAddDto.getEmail());
        if (!email.isEmpty()) {
            bindingResult.reject("email","이메일이 중복되었습니다");
        }
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
    public String member(@PathVariable("memberId") Long id,Authentication authentication, @Qualifier("post") @PageableDefault(size = 5) Pageable postsPageable, @Qualifier("comment") @PageableDefault(size = 5) Pageable commentsPageable,Model model) {
        MemberGetDto member = memberService.getMember(id);
        model.addAttribute("member", member);
        Page<PostsGetDto> posts = memberService.findPosts(id, postsPageable);
        Page<CommentDto> comments = memberService.findComments(id, commentsPageable);
        log.info("getTotalPages = {}" , posts.getTotalPages());
        if (member.getLoginId().equals(authentication.getName()) || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            model.addAttribute("flag",true);
        }
        model.addAttribute("postsPageable", postsPageable);
        model.addAttribute("commentsPageable", commentsPageable);
        model.addAttribute("posts", posts);
        model.addAttribute("comments", comments);
        return "members/member";
    }

    @GetMapping("/{memberId}/delete")
    public String deleteMember(@PathVariable("memberId") Long id, Authentication authentication, HttpServletRequest request,RedirectAttributes redirectAttributes) {
        String deleteMemberName = authentication.getName();
        Member member = memberRepository.findByLoginId(deleteMemberName).get();
        if(memberService.equalMember(member,id) || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            memberService.deleteMember(id);
            HttpSession session = request.getSession();
            if (session != null) {
                session.invalidate();
            }
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("status", Boolean.TRUE);
        return "redirect:/members/{memberId}";}

    @GetMapping("/find")
    public String findMember(@PageableDefault(size = 5) Pageable pageable, @RequestParam(value = "username",defaultValue = "") String username, Model model,Authentication authentication) {
        log.info("username = {} ", username);
        Page<MemberGetDto> members = memberService.getMemberName(username, pageable);
        log.info("members={} ", members);
        Member originMember = jpaMemberRepository.findByLoginId(authentication.getName());
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            model.addAttribute("admin_flag", true);
        }
        model.addAttribute("members", members);
        model.addAttribute("username",username);
        model.addAttribute("pageable", pageable);
        model.addAttribute("id", originMember.getId());
        return "members/members";
    }


    @GetMapping("/{memberId}/update")
    private String updateMember(@PathVariable("memberId") Long id, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        MemberGetDto member = memberService.getMember(id);
        model.addAttribute("member", member);
        if (authentication.getName().equals(member.getLoginId()) || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            return "members/editForm";
        }
        redirectAttributes.addFlashAttribute("status", Boolean.TRUE);
        return "redirect:/members/{memberId}";
    }

    @PostMapping("/{memberId}/update")
    private String update(@PathVariable("memberId") Long id, @Validated @ModelAttribute("member") MemberUpdateDto memberUpdateDto,BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "members/editForm";
        }
        memberService.editMember(id, memberUpdateDto);
        redirectAttributes.addAttribute("memberId", id);
        return "redirect:/members/{memberId}";
    }



    @GetMapping("/findMember")
    public String findIdAndPassword(Model model) {
        model.addAttribute("memberFindLoginIdDto", new MemberFindLoginIdDto());
        model.addAttribute("memberFindPasswordDto", new MemberFindPasswordDto());
        return "members/findForm";
    }

    @PostMapping("/find/loginId")
    public String findLoginId(@ModelAttribute MemberFindLoginIdDto memberFindDto,RedirectAttributes redirectAttributes) {
        log.info("memberFindDto = {} ", memberFindDto);
        String loginId = memberService.findLoginId(memberFindDto);
        log.info("loginId = {} ", loginId);
        if (loginId != null) {
            redirectAttributes.addFlashAttribute("status1", loginId);
        }
        else {
            redirectAttributes.addFlashAttribute("status3", "이름 또는 이메일이 잘못 입력되었습니다.");

        }
        return "redirect:/members/find";
    }

    @PostMapping("/find/password")
    public String findPassword(@ModelAttribute MemberFindPasswordDto memberFindPasswordDto, RedirectAttributes redirectAttributes) {
        String passWord = memberService.findPassWord(memberFindPasswordDto);
        log.info("password={}", passWord);
        if (passWord != null) {
            redirectAttributes.addFlashAttribute("status2", passWord);

        } else {
            redirectAttributes.addFlashAttribute("status3", "ID 또는 이름 또는 이메일이 잘못 입력되었습니다.");

        }
        return "redirect:/members/find";
    }
}

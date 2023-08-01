package firstProject.board.api;

import firstProject.board.repository.member.dto.MemberGetDto;
import firstProject.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping
    public Result membersApi() {
        List<MemberGetDto> members = memberService.getMembers();
        return new Result(members.size(), members);
    }

    @GetMapping("/{id}")
    public MemberGetDto memberApi(@PathVariable Long id) {
        MemberGetDto member = memberService.getMember(id);
        return member;
    }
}

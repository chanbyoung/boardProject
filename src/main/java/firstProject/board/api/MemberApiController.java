package firstProject.board.api;

import firstProject.board.domain.member.Member;
import firstProject.board.repository.member.MemberRepository;
import firstProject.board.repository.member.dto.MemberAddDto;
import firstProject.board.repository.member.dto.MemberGetDto;
import firstProject.board.repository.member.dto.MemberUpdateDto;
import firstProject.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/add")
    public ResponseEntity saveMemberApi(@Validated @RequestBody MemberAddDto memberAddDto) {
        Optional<Member> findLoginId = memberRepository.findByLoginId(memberAddDto.getLoginId());
        if (!findLoginId.isEmpty()) {
            return new ResponseEntity<>("아이디가 중복되었습니다", HttpStatus.BAD_REQUEST);
        }
        memberService.saveMember(memberAddDto);
        return new ResponseEntity(HttpStatus.OK);
    }

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

    @PutMapping("/{id}/edit")
    public MemberGetDto updateMemberApi(@PathVariable Long id, @Validated @RequestBody MemberUpdateDto memberUpdateDto) {
        memberService.editMember(id, memberUpdateDto);
        MemberGetDto member = memberService.getMember(id);
        return member;
    }
}

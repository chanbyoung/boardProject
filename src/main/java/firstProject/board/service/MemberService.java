package firstProject.board.service;

import firstProject.board.domain.member.Member;
import firstProject.board.repository.member.MemberAddDto;
import firstProject.board.repository.member.MemberGetDto;
import firstProject.board.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberGetDto getMember(Long id) {
        Member member = memberRepository.findById(id);
        MemberGetDto memberGetDto = new MemberGetDto(member);
        return memberGetDto;
    }

    public List<MemberGetDto> getMembers() {
        List<Member> members = memberRepository.findAll();
        List<MemberGetDto> result = members.stream().map(m -> new MemberGetDto(m)).collect(Collectors.toList());
        return result;
    }
    public boolean equalMember(Member loginMember, Long id) {
        Member member = memberRepository.findById(loginMember.getId());
        Member findMember = memberRepository.findById(id);
        return member.equals(findMember);
    }

    public Member saveMember(MemberAddDto memberAddDto) {
        Member member = new Member(memberAddDto.getLoginId(), memberAddDto.getPassword(), memberAddDto.getName(), memberAddDto.getBirth(), memberAddDto.getGender(), memberAddDto.getAddress());
        memberRepository.save(member);
        return member;
    }

    public void deleteMember(Long id) {
        memberRepository.delete(id);
    }
}

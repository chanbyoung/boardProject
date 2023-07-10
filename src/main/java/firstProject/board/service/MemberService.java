package firstProject.board.service;

import firstProject.board.domain.member.Member;
import firstProject.board.domain.member.MemberAddDto;
import firstProject.board.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
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

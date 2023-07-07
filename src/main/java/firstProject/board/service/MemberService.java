package firstProject.board.service;

import firstProject.board.domain.member.Member;
import firstProject.board.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public boolean equalMember(Member loginMember, Long id) {
        Member member = memberRepository.findById(loginMember.getId());
        Member findMember = memberRepository.findById(id);
        return member.equals(findMember);
    }
}

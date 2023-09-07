package firstProject.board;

import firstProject.board.domain.member.Address;
import firstProject.board.domain.member.Gender;
import firstProject.board.domain.member.Member;
import firstProject.board.repository.member.SpringDataJpaMemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultDataInt {
    private final SpringDataJpaMemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        List<Member> members = memberRepository.findAll();
        if (members.isEmpty()) {
            Member member = new Member("admin", "123", passwordEncoder, "admin@admin", "admin", "00000000", Gender.MALE, new Address("admin","admin","admin","admin"));
            member.updateRole();
            memberRepository.save(member);
        }
    }
}

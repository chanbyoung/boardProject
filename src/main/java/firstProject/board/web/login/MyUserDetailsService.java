package firstProject.board.web.login;

import firstProject.board.domain.member.Member;
import firstProject.board.repository.member.impl.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final JpaMemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> findMember = memberRepository.findByLoginId(username);
        Member member = findMember.orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다"));
        return User.builder()
                .username(member.getLoginId())
                .password(member.getPassword())
                .build();
    }
}

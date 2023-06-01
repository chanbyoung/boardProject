package firstProject.board.domain;

import firstProject.board.domain.member.Member;
import firstProject.board.domain.member.MemberRepository;
import firstProject.board.domain.post.Post;
import firstProject.board.domain.post.PostRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInt {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    @PostConstruct
    public void init(){
        postRepository.save(new Post("박찬병","안녕하세요","안녕하세요"));
        postRepository.save(new Post("김성민","안녕못해요","안녕못해요"));

        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("test!");
        member.setName("테스터");
        memberRepository.save(member);


    }
}

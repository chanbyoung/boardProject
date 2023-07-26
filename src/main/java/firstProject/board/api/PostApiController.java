package firstProject.board.api;

import firstProject.board.domain.member.Gender;
import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Post;
import firstProject.board.repository.member.MemberAddDto;
import firstProject.board.repository.post.SpringDataJpaPostRepository;
import firstProject.board.repository.post.impl.PostAddDto;
import firstProject.board.repository.post.impl.PostGetDto;
import firstProject.board.repository.post.impl.PostUpdateDto;
import firstProject.board.service.BoardService;
import firstProject.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostApiController {
    private final SpringDataJpaPostRepository postRepository;
    private final BoardService boardService;
    private final MemberService memberService;
    @GetMapping("/posts")
    public Result postsApi() {
        List<Post> findPosts = postRepository.findAll();

        List<PostGetDto> collect = findPosts.stream()
                .map(m -> PostGetDto.builder().
                        postNum(m.getPostNum()).
                        postName(m.getPostName()).
                        commentSize(m.getComments().size()).
                        memberName(m.getMember().getName()).
                        localDateTime(m.getLocalDateTime()).
                        readCount(m.getReadCount()).build())
                .collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    @GetMapping("/posts/{postId}")
    public PostGetDto getPost(@PathVariable("postId") Long postId) {
        Post post = postRepository.findById(postId).get();

        return PostGetDto.builder()
                .postNum(post.getPostNum())
                .postName(post.getPostName())
                .commentSize(post.getComments().size())
                .memberName(post.getMember().getName())
                .localDateTime(post.getLocalDateTime())
                .readCount(post.getReadCount()).build();
    }

    @PostMapping("/add")
    public PostAddDto addPost(@Validated @RequestBody PostAddDto postAddDto) {
        Post post = new Post(postAddDto.getPostName(), postAddDto.getContent());
        MemberAddDto member = new MemberAddDto(UUID.randomUUID().toString(), "test!", "테스트사용자", "00000000", Gender.MALE, "서울");
        Member testMember = memberService.saveMember(member);
        Long postId = boardService.savePost(post, testMember);
        return postAddDto.updatePostID(postId);
    }

    @PutMapping("/{id}/edit")
    public PostGetDto edit(@PathVariable("id") Long id, @Validated @RequestBody PostUpdateDto postUpdateDto) {
        boardService.editPost(id, postUpdateDto);
        return getPost(id);
    }

}

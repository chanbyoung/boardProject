package firstProject.board.api;

import firstProject.board.domain.member.Address;
import firstProject.board.domain.member.Gender;
import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Post;
import firstProject.board.repository.member.dto.MemberAddDto;
import firstProject.board.repository.post.SpringDataJpaPostRepository;
import firstProject.board.repository.post.dto.*;
import firstProject.board.service.BoardService;
import firstProject.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostApiController {
    private final SpringDataJpaPostRepository postRepository;
    private final BoardService boardService;
    private final MemberService memberService;
    @GetMapping("/posts")
    public Result postsApi(@PageableDefault Pageable pageable) {
        Page<PostsGetDto> posts = boardService.getPosts(new PostSearchCond(), pageable);
        return new Result(posts.getSize(), posts);
    }

    @GetMapping("/posts/{postId}")
    public PostGetDto getPostApi(@PathVariable("postId") Long postId) {
        return boardService.getPost(postId);
    }

    @PostMapping("/add")
    public PostAddDto addPostApi(@Validated @RequestBody PostAddDto postAddDto) {
        Post post = new Post(postAddDto.getPostName(), postAddDto.getContent());
        MemberAddDto member = new MemberAddDto(UUID.randomUUID().toString(), "test!", "테스트사용자", "00000000", Gender.MALE, new Address("123","123","123","123"));
        Member testMember = memberService.saveMember(member);
        Long postId = boardService.savePost(post, testMember);
        return postAddDto.updatePostID(postId);
    }

    @PutMapping("/{id}/edit")
    public PostGetDto editApi(@PathVariable("id") Long id, @Validated @RequestBody PostUpdateDto postUpdateDto) {
        boardService.editPost(id, postUpdateDto);
        return getPostApi(id);
    }

    @PostMapping("/{id}/delete")
    public String deleteApi(@PathVariable Long id) {
        boardService.deletePost(id);
        return "ok";
    }

//    @PostMapping("/{id}/comment")
//    public String addCommentApi(@PathVariable Long id, @Validated @RequestBody CommentDto commentDto) {
//        boardService.saveComment(id,new Member(100L,UUID.randomUUID().toString(), "test!", "테스트사용자", "00000000", Gender.MALE, new Address("123","123","123","123")), commentDto);
//        return "redirect:/api/posts/{id}";
//    }

}

package firstProject.board.service;

import firstProject.board.domain.member.Gender;
import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Comment;
import firstProject.board.domain.post.Post;
import firstProject.board.repository.member.MemberRepository;
import firstProject.board.repository.post.CommentRepository;
import firstProject.board.repository.post.PostRepository;
import firstProject.board.repository.post.dto.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired BoardService boardService;
    @Autowired PostRepository postRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired EntityManager em;

    @Test
    public void getPostTest() throws Exception {
        //given
        Member member = new Member("123","123","pcb","20000728", Gender.MALE,"seoul");
        memberRepository.save(member);
        Post post = new Post("new post", "new content");
        post.updateMember(member);
        postRepository.save(post);

        //when
        PostGetDto postGetDto = boardService.getPost(post.getId());

        //then
        assertThat(post.getId()).isEqualTo(postGetDto.getId());
        assertThat(post.getPostName()).isEqualTo(postGetDto.getPostName());
        assertThat(post.getContent()).isEqualTo(postGetDto.getContent());
        assertThat(post.getMember().getName()).isEqualTo(postGetDto.getMemberName());
    }

    @Test
    public void getPostsTest() throws Exception {
        //given
        Post post1 = new Post("1", "new content");
        Post post2 = new Post("2", "new content");
        Post post3 = new Post("3", "new content");
        Post post4 = new Post("new post", "new content");
        Post post5 = new Post("new post", "new content");
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);
        postRepository.save(post5);
        PageRequest pageable = PageRequest.of(0, 3);

        //when
        Page<PostsGetDto> posts = boardService.getPosts(new PostSearchCond(), pageable);

        //then
        assertThat(posts.getSize()).isEqualTo(3);
        assertThat(posts.getTotalPages()).isEqualTo(2);
    }

    @Test
    public void savePostTest() throws Exception {
        //given
        Member member = new Member("123","123","pcb","20000728", Gender.MALE,"seoul");
        memberRepository.save(member);
        Post post = new Post("new post", "new content");

        //when
        Long newPostId = boardService.savePost(post, member);


        //then
        assertThat(newPostId).isEqualTo(post.getId());
    }

    @Test
    public void deletePostTest() throws Exception {
        //give
        Post post = new Post("new post", "new content");
        postRepository.save(post);
        Long postId = post.getId();

        //when
        boardService.deletePost(post.getId());

        //then
        assertThatThrownBy(() ->postRepository.findById(postId))
                .isInstanceOf(EmptyResultDataAccessException.class);

    }

    @Test
    public void editPostTest() throws Exception {
        //given
        Post post = new Post("new post", "new content");
        postRepository.save(post);
        PostUpdateDto postUpdateDto = new PostUpdateDto("edit post", "edit content");

        //when
        boardService.editPost(post.getId(), postUpdateDto);

        //then
        assertThat(post.getPostName()).isEqualTo(postUpdateDto.getPostName());
        assertThat(post.getContent()).isEqualTo(postUpdateDto.getContent());
    }

    @Test
    public void saveCommentTest() throws Exception {
        //given
        Member member = new Member("123","123","pcb","20000728", Gender.MALE,"seoul");
        memberRepository.save(member);
        Post post = new Post("new post", "new content");
        post.updateMember(member);
        postRepository.save(post);
        CommentDto commentDto = new CommentDto();
        commentDto.setContent("new comment");
        //when
        boardService.saveComment(post.getId(), member, commentDto);
        em.flush();
        em.clear();
        Post findPost = postRepository.findById(post.getId());

        //then
        assertThat(findPost.getComments().size()).isEqualTo(1);
    }

    @Test
    public void deleteComment()throws Exception {
        //given
        Comment comment = new Comment(new Member(), "content", new Post(), 1L);
        Comment saveComment = commentRepository.save(comment);
        Long findCommentId = saveComment.getId();

        //when
        boardService.deleteComment(findCommentId);
        Optional<Comment> findComment = commentRepository.findById(findCommentId);

        //then
        assertThat(findComment).isEmpty();
    }

}
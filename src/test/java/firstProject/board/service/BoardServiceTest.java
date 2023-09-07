package firstProject.board.service;

import firstProject.board.domain.member.Address;
import firstProject.board.domain.member.Gender;
import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Post;
import firstProject.board.repository.member.SpringDataJpaMemberRepository;
import firstProject.board.repository.post.CommentRepository;
import firstProject.board.repository.post.PostRepository;
import firstProject.board.repository.post.dto.PostGetDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @InjectMocks BoardServiceImpl boardService;
    @Spy PostRepository postRepository;
    @Spy
    SpringDataJpaMemberRepository memberRepository;
    @Spy CommentRepository commentRepository;
    @Autowired  EntityManager em;

    @Test
    public void getPostTest() throws Exception {
        //given
        Long postId = 1L;
        Post mockPost = new Post(postId, "Post", "content");
        mockPost.updateMember(new Member(postId,"123","123","123@123.com","pcb","20000728", Gender.MALE,new Address()));
        when(postRepository.findById(postId)).thenReturn(mockPost);

        //when
        PostGetDto post = boardService.getPost(postId);


        //then
        verify(postRepository, times(1)).findById(postId);
        assertThat(post.getId()).isEqualTo(mockPost.getId());
        assertThat(post.getPostName()).isEqualTo(mockPost.getPostName());
        assertThat(post.getContent()).isEqualTo(mockPost.getContent());
    }

//    @Test
//    public void getPostsTest() throws Exception {
//        //given
//        PostSearchCond postSearchCond = new PostSearchCond();
//        PageRequest pageable = PageRequest.of(0, 3);
//        PostsGetDto postsGetDto = mock(PostsGetDto.class);
//        when(postRepository.findAll(postSearchCond,pageable)).thenReturn(new Page<PostsGetDto>);
//
//        //when
//        Page<PostsGetDto> posts = boardService.getPosts(new PostSearchCond(), pageable);
//
//        //then
//        assertThat(posts.getSize()).isEqualTo(3);
//        assertThat(posts.getTotalPages()).isEqualTo(2);
//    }

    @Test
    public void savePostTest() throws Exception {
        //given
        Long postId = 1L;
        Post mockPost = new Post(postId, "Post", "content");
        Member mockMember = new Member(postId,"123", "123","123@123.com", "pcb", "20000728", Gender.MALE, new Address());
        when(postRepository.save(mockPost)).thenReturn(postId);
        when(memberRepository.findByLoginId(any())).thenReturn(mockMember);
        //when
        Long newPostId = boardService.savePost(mockPost, mockMember.getLoginId());
        //then
        assertThat(newPostId).isEqualTo(mockPost.getId());
    }

    @Test
    public void deletePostTest() throws Exception {
        //give
        Long postId = 1L;
        Post mockPost = new Post("Post", "content");
        when(postRepository.save(mockPost)).thenReturn(postId);
        when(postRepository.findById(postId)).thenThrow(EmptyResultDataAccessException.class);
        Long savePostId = postRepository.save(mockPost);

        //when
        boardService.deletePost(savePostId);

        //then
        assertThatThrownBy(() ->postRepository.findById(postId))
                .isInstanceOf(EmptyResultDataAccessException.class);

    }

//    @Test
//    public void editPostTest() throws Exception {
//        //given
//        Long postId = 1L;
//        Post mockPost = new Post("new post", "new content");
//        PostUpdateDto postUpdateDto = new PostUpdateDto("edit post", "edit content");
//        when(postRepository.save(mockPost)).thenReturn(postId);
//        when(em.find(Post.class, postId)).thenReturn(mockPost);
//        Long savePostId = postRepository.save(mockPost);
//
//        //when
//        boardService.editPost(savePostId, postUpdateDto);
//
//        //then
//        assertThat(mockPost.getPostName()).isEqualTo(postUpdateDto.getPostName());
//        assertThat(mockPost.getContent()).isEqualTo(postUpdateDto.getContent());
//    }

//    @Test
//    public void saveCommentTest() throws Exception {
//        //given
//        Long postId = 1L;
//        Member member = new Member(postId,"123","123","pcb7893@naver.com","pcb","20000728", Gender.MALE,new Address());
//        Post post = new Post("new post", "new content");
//        CommentDto commentDto = new CommentDto(postId,"new post", "new content");
//        wtlhen(postRepository.findById(postId)).thenReturn(post);
//        when(memberRepository.findByLoginId(any())).thenReturn(member);
//        when(commentRepository.countCommentByPostId(postId)).thenReturn(0L);
//        //when
//        boardService.saveComment(post.getId(), any(), commentDto);
//        Post findPost = postRepository.findById(post.getId());
//
//        //then
//        assertThat(findPost.getComments().size()).isEqualTo(1);
//    }
//
//    @Test
//    public void deleteComment()throws Exception {
//        //given
//        Comment comment = new Comment(new Member(), "content", new Post(), 1L);
//        Comment saveComment = commentRepository.save(comment);
//        Long findCommentId = saveComment.getId();
//
//        //when
//
//        boardService.deleteComment(findCommentId);
//        Optional<Comment> findComment = commentRepository.findById(findCommentId);
//
//        //then
//        assertThat(findComment).isEmpty();
//    }

}
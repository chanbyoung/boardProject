package firstProject.board.service;

import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Comment;
import firstProject.board.domain.post.Post;
import firstProject.board.repository.member.SpringDataJpaMemberRepository;
import firstProject.board.repository.post.CommentRepository;
import firstProject.board.repository.post.PostRepository;
import firstProject.board.repository.post.dto.*;
import firstProject.board.repository.post.impl.JpaCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardServiceImpl implements BoardService{
    private final PostRepository postRepository;
    private final JpaCommentRepository jplCmtRepository;
    private final CommentRepository commentRepository;
    private final SpringDataJpaMemberRepository jpaMemberRepository;

    @Override
    public PostGetDto getPost(Long id) {
        return new PostGetDto(postRepository.findById(id));

    }

    @Override
    public Page<PostsGetDto> getPosts(PostSearchCond cond, Pageable pageable) {
       return postRepository.findAll(cond, pageable);
    }


    @Override
    public Long savePost(Post post,String loginId) {
        post.updateMember(jpaMemberRepository.findByLoginId(loginId));
        Long postId = postRepository.save(post);
        return postId;
    }
    @Override
    public void deletePost(Long id) {
        postRepository.delete(id);
    }

    @Override
    public void editPost(Long id, PostUpdateDto editParam) {
        postRepository.update(id,editParam);

    }

    @Override
    public Page<CommentGetDto> getComment(Long postId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findCommentByPostId(postId, pageable);
        Page<CommentGetDto> reComments = comments.map(c -> new CommentGetDto(c));
        return reComments;


    }

    @Override
    public void saveComment(Long id, String loginId, CommentDto commentDto) {
        Post findPost = postRepository.findById(id);
        Member member = jpaMemberRepository.findByLoginId(loginId);
        Long cnt = commentRepository.countCommentByPostId(id);
        Comment comment = new Comment(member, commentDto.getContent(), findPost, cnt);
        commentRepository.save(comment);
    }

    @Override
    public Boolean deleteComment( Long commentId,Long postId, String loginId) {
        Comment comment = commentRepository.findById(commentId).get();
        if (comment.getMember().getLoginId() == loginId) {
            Long commentNum = comment.getCommentNum();
            commentRepository.delete(comment);
            jplCmtRepository.updateCommentNum(postId,commentNum);
            return true;
        }
        return false;
    }

    @Override
    public void viewCountUp(Long id) {
        Post post = postRepository.findById(id);
        post.updateReadCount();
    }

    @Override
    public void updateComment(Long commentId, String content) {
        jplCmtRepository.updateCommentContent(commentId, content);
    }
}

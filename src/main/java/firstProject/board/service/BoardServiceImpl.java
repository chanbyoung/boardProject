package firstProject.board.service;

import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Comment;
import firstProject.board.domain.post.Post;
import firstProject.board.repository.member.MemberRepository;
import firstProject.board.repository.post.CommentRepository;
import firstProject.board.repository.post.PostRepository;
import firstProject.board.repository.post.impl.CommentDto;
import firstProject.board.repository.post.impl.PostSearchCond;
import firstProject.board.repository.post.impl.PostUpdateDto;
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
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Override
    public Page<Post> getPosts(PostSearchCond cond, Pageable pageable) {
       return postRepository.findAll(cond, pageable);
    }


    @Override
    public Post savePost(Post post,Member member) {
        post.updateMember(memberRepository.findById(member.getId()));
        Post savePost = postRepository.save(post);
        return savePost;
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
    public void saveComment(Long id, Member member, CommentDto commentDto) {
        Post findPost = postRepository.findById(id);
        Comment comment = new Comment(member, commentDto.getContent(), findPost);
        commentRepository.save(comment);
    }

    @Override
    public Long deleteComment( Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        Post post = comment.getPost();
        commentRepository.delete(comment);
        return post.getId();
    }

    @Override
    public void updateReadCount(Post post, long id) {
        post.updateReadCount();
    }
}

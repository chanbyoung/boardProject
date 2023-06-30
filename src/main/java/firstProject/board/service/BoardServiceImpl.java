package firstProject.board.service;

import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Comment;
import firstProject.board.domain.post.Post;
import firstProject.board.domain.post.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    @Override
    public List<Post> getPosts(PostSearchCond cond) {
        List<Post> posts = postRepository.findAll(cond);
        return posts;
    }

    @Override
    public Post savePost(Post post, Member member) {
        post.setName(member.getName());
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
        Comment comment = new Comment(member.getName(), commentDto.getContent(), findPost);
        commentRepository.save(comment);
    }

    @Override
    public Long deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        Post post = comment.getPost();
        commentRepository.delete(comment);
        return post.getId();
    }
}

package firstProject.board.service;

import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Post;
import firstProject.board.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final PostRepository postRepository;
    @Override
    public List<Post> getPosts() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    @Override
    public Post savePost(Post post, Member member) {
        post.setName(member.getName());
        post.setLocalDateTime(LocalDateTime.now());
        Post savePost = postRepository.save(post);
        return savePost;
    }

    @Override
    public void editPost(Long id, Post editParam) {
        postRepository.update(id,editParam);
    }
}

package firstProject.board.domain.post;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepository {
    private static final Map<Long, Post> store = new HashMap<>();
    private static long sequence = 0L;

    public Post save(Post post){
        post.setId(++sequence);
        store.put(post.getId(), post);
        return post;
    }
    public Post findById(Long id){
        return store.get(id);
    }

    public List<Post> findAll(){
        return new ArrayList<>(store.values());
    }
    public void update(Long id, Post updateParam){
        Post findPost = findById(id);
        findPost.setPostName(updateParam.getPostName());
        findPost.setContent(updateParam.getContent());
    }

}

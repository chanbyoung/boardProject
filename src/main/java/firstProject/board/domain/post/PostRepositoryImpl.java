package firstProject.board.domain.post;

import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private static final Map<Long, Post> store = new HashMap<>();
    private static long sequence = 0L;
    private static Long readCount = 0L;

    @Override
    public Post save(Post post) {
        post.setId(++sequence);
        post.setReadCount(readCount);
        store.put(post.getId(), post);
        return post;
    }

    @Override
    public Map saveComment(Post post, Comment comment) {
        Map<Long, Comment> commentList = post.getCommentList();
        int size = commentList.size();
        commentList.put((long) ++size, comment);
        return commentList;
    }

    @Override
    public Post findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<Post> findAll(PostSearchCond cond) {
        String type = cond.getType();
        String searchContent = cond.getSearchContent();
        return store.values().stream()
                .filter(post -> {
                    if (ObjectUtils.isEmpty(searchContent)) {
                        return true;
                    }
                    if (type.equals("name")) {
                        return post.getName().contains(searchContent);
                    }
                    if (type.equals("postName"))
                        return post.getPostName().contains(searchContent);
                    if (type.equals("content"))
                        return post.getContent().contains(searchContent);
                    return true;
                }).collect(Collectors.toList());
    }


    @Override
    public void update(Long id, Post updateParam){
        Post findPost = findById(id);
        findPost.setPostName(updateParam.getPostName());
        findPost.setContent(updateParam.getContent());
    }
    @Override
    public void updateReadCount(Long id){
        Post updatePost = findById(id);
        updatePost.setReadCount(updatePost.getReadCount()+1);
    }

}

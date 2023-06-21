package firstProject.board.domain.post.repository.impl;

import firstProject.board.domain.post.Post;
import firstProject.board.domain.post.repository.PostRepository;
import firstProject.board.domain.post.repository.PostSearchCond;
import firstProject.board.domain.post.repository.PostUpdateDto;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Repository
public class memoryPostRepository implements PostRepository {
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

//    @Override
//    public void saveComment(Post post, Comment comment) {
//        Map<Long, Comment> commentList = post.getCommentList();
//        int size = commentList.size();
//        commentList.put((long) ++size, comment);
//    }

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
//                        return post.getName().contains(searchContent);
                    }
                    if (type.equals("postName"))
                        return post.getPostName().contains(searchContent);
                    if (type.equals("content"))
                        return post.getContent().contains(searchContent);
                    return true;
                }).collect(Collectors.toList());
    }


    @Override
    public void update(Long id, PostUpdateDto updateParam){
        Post findPost = findById(id);
        findPost.setPostName(updateParam.getPostName());
        findPost.setContent(updateParam.getContent());
    }
    @Override
    public void updateReadCount(Long id){
        Post updatePost = findById(id);
        updatePost.setReadCount(updatePost.getReadCount()+1);
    }

    @Override
    public void delete(Long id) {

    }
}

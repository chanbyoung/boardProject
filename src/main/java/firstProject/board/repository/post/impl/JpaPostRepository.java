package firstProject.board.repository.post.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import firstProject.board.domain.post.Post;
import firstProject.board.repository.post.FileRepository;
import firstProject.board.repository.post.PostRepository;
import firstProject.board.repository.post.PostSearchCond;
import firstProject.board.repository.post.PostUpdateDto;
import firstProject.board.repository.post.SpringDataJpaPostRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static firstProject.board.domain.post.QPost.post;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JpaPostRepository implements PostRepository {

    private final EntityManager em;
    private final SpringDataJpaPostRepository repository;
    private final FileRepository fileRepository;
    private final JPAQueryFactory query;


    @Override
    public Post save(Post post) {
        em.persist(post);
        return post;
    }


    @Override
    public Post findById(Long id) {
        Post post = em.find(Post.class, id);
        return post;
    }

    @Override
    public List<Post> findAll(PostSearchCond cond) {
        String type = cond.getType();
        String searchContent = cond.getSearchContent();
        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(type)){
            if(type.equals("name")){
                if (StringUtils.hasText(searchContent)){
                    builder.and(post.member.name.like("%"+searchContent+"%"));
                }
            }
            if(type.equals("postName")){
                if (StringUtils.hasText(searchContent)){
                    builder.and(post.postName.like("%"+searchContent+"%"));
                }
            }
            if(type.equals("content")){
                if (StringUtils.hasText(searchContent)){
                    builder.and(post.content.like("%"+searchContent+"%"));
                }
            }
        }
        List<Post> result = query
                .select(post)
                .from(post)
                .where(builder)
                .orderBy(post.id.desc())
                .fetch();
        return result;
    }

    @Override
    public void update(Long id, PostUpdateDto updateParam) {
        Post post = em.find(Post.class, id);
        post.updatePost(updateParam.getPostName(), updateParam.getContent());
    }

    @Override
    public void updateReadCount(Long id) {
        Post post = em.find(Post.class, id);
        post.UpdateReadCount(post.getReadCount()+1);
    }

    @Override
    public void delete(Long id) {
        Post post = em.find(Post.class, id);
        repository.delete(post);

    }
}

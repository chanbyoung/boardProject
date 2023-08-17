package firstProject.board.repository.post.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import firstProject.board.domain.member.QMember;
import firstProject.board.domain.post.Post;
import firstProject.board.repository.post.PostRepository;
import firstProject.board.repository.post.SpringDataJpaPostRepository;
import firstProject.board.repository.post.dto.PostSearchCond;
import firstProject.board.repository.post.dto.PostUpdateDto;
import firstProject.board.repository.post.dto.PostsGetDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static firstProject.board.domain.post.QPost.post;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JpaPostRepository implements PostRepository {

    private final EntityManager em;
    private final SpringDataJpaPostRepository repository;
    private final JPAQueryFactory query;


    @Override
    public Long save(Post post) {
        Long cnt = repository.countPostBy();
        post.updatePostNum(cnt+1);
        em.persist(post);
        return post.getId();
    }


    @Override
    public Post findById(Long id) {
        Post post = em.createQuery(
                        "select p" +
                                " from Post p" +
                                " join fetch p.member m" +
                                " where p.id = :postId", Post.class)
                .setParameter("postId", id)
                .getSingleResult();
        return post;

    }

    @Override
    public Page<PostsGetDto> findAll(PostSearchCond cond, Pageable pageable) {
        String type = cond.getType();
        String searchContent = cond.getSearchContent();
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(type)) {
            if (type.equals("name")) {
                if (StringUtils.hasText(searchContent)) {
                    builder.and(post.member.name.contains(searchContent));
                }
            }
            if (type.equals("postName")) {
                if (StringUtils.hasText(searchContent)) {
                    builder.and(post.postName.contains(searchContent));
                }
            }
            if (type.equals("content")) {
                if (StringUtils.hasText(searchContent)) {
                    builder.and(post.content.contains(searchContent));
                }
            }
        }
        List<Post> posts = query
                .select(post)
                .from(post)
                .leftJoin(post.member, QMember.member)
                .fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.id.desc())
                .distinct()
                .fetch();

        List<PostsGetDto> result = posts.stream().map(p -> new PostsGetDto(p)).collect(Collectors.toList());


        long count = query
                .select(post.count())
                .from(post)
                .where(builder)
                .fetchOne();
        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public void update(Long id, PostUpdateDto updateParam) {
        Post post = em.find(Post.class, id);
        post.updatePost(updateParam.getPostName(), updateParam.getContent());
    }

    @Override
    public void delete(Long id) {
        Post post = em.find(Post.class, id);
        Long postNum = post.getPostNum();
        repository.delete(post);

        em.createQuery("update Post p" +
                " set p.postNum = p.postNum-1" +
                " where p.postNum> :postNum")
                .setParameter("postNum", postNum)
                .executeUpdate();
    }
}

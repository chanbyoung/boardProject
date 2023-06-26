package firstProject.board.domain.post.repository.impl.old;

import firstProject.board.domain.post.Post;
import firstProject.board.domain.post.repository.PostSearchCond;
import firstProject.board.domain.post.repository.PostUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
@Slf4j
//@Repository
public class JdbcPostRepository {
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcPostRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("post")
                .usingGeneratedKeyColumns("id");
    }

//    @Override
    public Post save(Post post) {
        post.setReadCount(0L);
        SqlParameterSource param = new BeanPropertySqlParameterSource(post);
        Number key = jdbcInsert.executeAndReturnKey(param);
        post.setId(key.longValue());
        return post;
    }

//    @Override
//    public void saveComment(Post post, Comment comment) {
//        String sql ="update post set commentList=:commentList where =:id";
//        Map<Long, Comment> commentList = post.getCommentList();
//        int size = commentList.size();
//        commentList.put((long) ++size, comment);
//        MapSqlParameterSource param = new MapSqlParameterSource().addValue(
//                "commentList", commentList)
//                .addValue("id", post.getId());
//        template.update(sql, param);
//    }

//    @Override
    public Post findById(Long id) {
        String sql = "select * from post where id= :id";
        Map<String, Long> param = Map.of("id",id);
        return template.queryForObject(sql, param, postRowMapper());
    }

    private RowMapper<Post> postRowMapper() {
        return BeanPropertyRowMapper.newInstance(Post.class);
    }

//    @Override
    public List<Post> findAll(PostSearchCond cond) {
        String type = cond.getType();
        String searchContent = cond.getSearchContent();
        SqlParameterSource param = new BeanPropertySqlParameterSource(cond);
        String sql = "select * from post";
        if(StringUtils.hasText(type)){
            sql += " where";
            if(type.equals("name")){
                sql += " name like concat('%',:searchContent,'%')";
            }
            if(type.equals("postName")){
                sql += " post_name like concat('%',:searchContent,'%')";
            }
            if(type.equals("content")){
                sql += " content like concat('%',:searchContent,'%')";
            }
        }
        log.info("sql={}", sql);
        return template.query(sql, param, postRowMapper());
    }

//    @Override
    public void update(Long id, PostUpdateDto updateParam) {
        String sql = "update post set post_name=:postName, content=:content where id=:id";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("postName", updateParam.getPostName())
                .addValue("content", updateParam.getContent())
                .addValue("id", id);
        template.update(sql, param);
    }

//    @Override
    public void updateReadCount(Long id) {
        String sql = "update post set read_count=:readCount where id=:id";
        Post updatePost = findById(id);
        MapSqlParameterSource param = new MapSqlParameterSource().addValue(
                        "readCount", updatePost.getReadCount() + 1)
                .addValue("id", id);
        template.update(sql, param);
    }

//    @Override
    public void delete(Long id) {
        String sql = "delete from post where id =:id";
        Map<String, Long> param = Map.of("id", id);
        template.update(sql, param);
    }
}

package firstProject.board.domain.member.impl;

import firstProject.board.domain.member.Member;
import firstProject.board.domain.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
//@Repository
public class JdbcMemberRepository implements MemberRepository {
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcMemberRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public void save(Member member) {
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(member);
        Number key = jdbcInsert.executeAndReturnKey(param);
        member.setId(key.longValue());
//        return member;
    }

    @Override
    public Member findById(Long id) {
        String sql = "select * from member where id=:id";
        Map<String, Long> param = Map.of("id", id);
        return template.queryForObject(sql,param,memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
        return BeanPropertyRowMapper.newInstance(Member.class);
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * form member";
        return template.query(sql,memberRowMapper());
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        String sql = "select * from member where login_id=:loginId";
        try {
            Map<String, String> param = Map.of("loginId", loginId);
            Member member = template.queryForObject(sql, param, memberRowMapper());
            return Optional.of(member);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();

        }

    }
}

package firstProject.board.domain.member.impl;

import firstProject.board.domain.member.Member;
import firstProject.board.domain.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
//@Repository
public class memoryMemberRepository implements MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    public void save(Member member){
        member.setId(++sequence);
        log.info("save: member = {}", member);
        store.put(sequence, member);
//        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Member> findByLoginId(String loginId){
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findAny();
    }



}


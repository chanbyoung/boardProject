package firstProject.board.service;

import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Comment;
import firstProject.board.domain.post.Post;
import firstProject.board.repository.member.MemberRepository;
import firstProject.board.repository.member.SpringDataJpaMemberRepository;
import firstProject.board.repository.member.dto.MemberAddDto;
import firstProject.board.repository.member.dto.MemberGetDto;
import firstProject.board.repository.member.dto.MemberUpdateDto;
import firstProject.board.repository.member.dto.find.MemberFindLoginIdDto;
import firstProject.board.repository.member.dto.find.MemberFindPasswordDto;
import firstProject.board.repository.post.CommentRepository;
import firstProject.board.repository.post.SpringDataJpaPostRepository;
import firstProject.board.repository.post.dto.CommentDto;
import firstProject.board.repository.post.dto.PostsGetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final SpringDataJpaMemberRepository jpaMemberRepository;
    private final SpringDataJpaPostRepository jpaPostRepository;
    private final CommentRepository jpaCommentRepository;
    private final PasswordEncoder passwordEncoder;
    public MemberGetDto getMember(Long id) {
        Member member = memberRepository.findById(id);
        MemberGetDto memberGetDto = new MemberGetDto(member);
        return memberGetDto;
    }

    public List<MemberGetDto> getMembers() {
        List<Member> members = memberRepository.findAll();
        List<MemberGetDto> result = members.stream().map(m -> new MemberGetDto(m)).collect(Collectors.toList());
        return result;
    }

    public Page<MemberGetDto> getMemberName(String name, Pageable pageable) {
        Page<Member> members = jpaMemberRepository.findMemberByMemberName(name, pageable);
        Page<MemberGetDto> reMembers = members.map(m -> new MemberGetDto(m));
        return reMembers;
    }
    public boolean equalMember(Member loginMember, Long id) {
        Member member = memberRepository.findById(loginMember.getId());
        Member findMember = memberRepository.findById(id);
        return member.equals(findMember);
    }

    public Member saveMember(MemberAddDto memberAddDto) {
        Member member = new Member(memberAddDto.getLoginId(), passwordEncoder.encode(memberAddDto.getPassword()), memberAddDto.getEmail(),memberAddDto.getName(), memberAddDto.getBirth(), memberAddDto.getGender(), memberAddDto.getAddress());
        memberRepository.save(member);
        return member;
    }

    public void deleteMember(Long id) {
        memberRepository.delete(id);
    }

    public void editMember(Long id, MemberUpdateDto memberUpdateDto) {
        memberRepository.update(id, memberUpdateDto);
    }

    public Page<PostsGetDto> findPosts(Long id, Pageable pageRequest) {
        Page<Post> posts = jpaPostRepository.findPostByMemberId(id, pageRequest);
        Page<PostsGetDto> rePosts = posts.map(p -> new PostsGetDto(p));
        return rePosts;
    }

    public Page<CommentDto> findComments(Long id, Pageable postsPageable) {
        Page<Comment> comments = jpaCommentRepository.findCommentByMemberId(id, postsPageable);
        Page<CommentDto> reComments = comments.map(c -> new CommentDto(c));
        return reComments;
    }

    public String findLoginId(MemberFindLoginIdDto memberFindLoginIdDto) {
        String loginId = jpaMemberRepository.findLoginIdByMemberInfo(memberFindLoginIdDto.getName(), memberFindLoginIdDto.getEmail());
        return loginId;
    }

    public Long findPassWordDtoByMemberId(MemberFindPasswordDto memberFindPasswordDto) {
        Optional<Member> findMember = jpaMemberRepository.findMemberByMemberPasswordDto(memberFindPasswordDto.getName(), memberFindPasswordDto.getLoginId(), memberFindPasswordDto.getEmail());
        if (findMember.isEmpty()) {
            return null;
        }
        else return findMember.get().getId();
    }

    public void changePassword(Long id, String password) {
        Member member = memberRepository.findById(id);
        member.updatePassword(passwordEncoder.encode(password));
    }

    public Optional<Member> findEmail(String email) {
        return jpaMemberRepository.findEmailExist(email);
    }
}

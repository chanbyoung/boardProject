package firstProject.board.repository.member.dto;

import firstProject.board.domain.member.Address;
import firstProject.board.domain.member.Gender;
import firstProject.board.domain.member.Member;
import firstProject.board.repository.post.dto.CommentDto;
import firstProject.board.repository.post.dto.PostGetDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MemberApiGetDto {
    private Long id;
    private String loginId;
    private String name;
    private String birth;
    private Gender gender;
    private Address address;
    private List<PostGetDto> posts;
    private List<CommentDto> comments;

    public MemberApiGetDto(Member member) {
        this.id= member.getId();
        this.loginId = member.getLoginId();
        this.name = member.getName();
        this.birth = member.getBirth();
        this.gender = member.getGender();
        this.address = member.getAddress();
        this.posts = member.getPosts().stream()
                .map(post -> new PostGetDto(post))
                .collect(Collectors.toList());
        this.comments = member.getComments().stream()
                .map(comment -> new CommentDto(comment))
                .collect(Collectors.toList());
    }
}

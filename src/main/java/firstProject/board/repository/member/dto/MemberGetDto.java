package firstProject.board.repository.member.dto;

import firstProject.board.domain.member.Address;
import firstProject.board.domain.member.Member;
import firstProject.board.domain.member.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberGetDto {
    private Long id;
    private String loginId;
    private String email;
    private String name;
    private String birth;
    private String gender;
    private Address address;
    private Role role;


    public MemberGetDto() {
    }

    public MemberGetDto(Member member) {
        this.id= member.getId();
        this.loginId = member.getLoginId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.birth = member.getBirth();
        this.gender = member.getGender().getDescription();
        this.address = member.getAddress();
        this.role = member.getRole();
    }
}

package firstProject.board.repository.member.dto;

import firstProject.board.domain.member.Address;
import firstProject.board.domain.member.Gender;
import firstProject.board.domain.member.Member;
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
    private Gender gender;
    private Address address;


    public MemberGetDto() {
    }

    public MemberGetDto(Member member) {
        this.id= member.getId();
        this.loginId = member.getLoginId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.birth = member.getBirth();
        this.gender = member.getGender();
        this.address = member.getAddress();
    }
}

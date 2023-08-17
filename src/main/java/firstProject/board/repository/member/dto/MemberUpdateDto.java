package firstProject.board.repository.member.dto;

import firstProject.board.domain.member.Address;
import firstProject.board.domain.member.Member;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateDto {
    private Long id;
    private String loginId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @NotEmpty
    private String birth;
    @Valid
    private Address address;

    public MemberUpdateDto() {
    }

    public MemberUpdateDto(Member member) {
        this.id = member.getId();
        this.loginId = member.getLoginId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.birth = member.getBirth();
        this.address = member.getAddress();
    }
}

package firstProject.board.repository.member.dto;

import firstProject.board.domain.member.Member;
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
    private String birth;
    @NotEmpty
    private String address;

    public MemberUpdateDto() {
    }

    public MemberUpdateDto(Member member) {
        this.id = member.getId();
        this.loginId = member.getLoginId();
        this.name = member.getName();
        this.birth = member.getBirth();
        this.address = member.getAddress();
    }
}
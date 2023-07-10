package firstProject.board.repository.member;

import firstProject.board.domain.member.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberAddDto {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name; //사용자 이름
    @NotEmpty
    private String birth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotEmpty
    private String address;
}

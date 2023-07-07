package firstProject.board.domain.member;

import jakarta.persistence.Column;
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
    @Column(length = 8)
    private String birth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotEmpty
    private String address;
}

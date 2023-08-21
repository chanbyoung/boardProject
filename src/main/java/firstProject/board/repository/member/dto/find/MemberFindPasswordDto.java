package firstProject.board.repository.member.dto.find;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFindPasswordDto {
    private String name;
    private String email;
    private String loginId;

    public MemberFindPasswordDto() {
    }
}

package firstProject.board.repository.member.dto.find;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFindLoginIdDto {
    private String email;
    private String name;

    public MemberFindLoginIdDto() {
    }

}

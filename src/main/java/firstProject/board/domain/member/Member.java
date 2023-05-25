package firstProject.board.domain.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Member {
    private Long id; //데이터 베이스에 관리되는 id
    @NotEmpty
    private String name; //사용자 이름
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
}

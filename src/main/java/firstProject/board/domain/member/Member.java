package firstProject.board.domain.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; //데이터 베이스에 관리되는 id
    @NotEmpty
    private String name; //사용자 이름
    @NotEmpty
//    @Column(name = "login_id")
    private String loginId;
    @NotEmpty
    private String password;
}

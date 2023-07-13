package firstProject.board.domain.member;

import firstProject.board.domain.post.Comment;
import firstProject.board.domain.post.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@ToString
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; //데이터 베이스에 관리되는 id

    @NotEmpty
    @Column(unique = true)
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name; //사용자 이름
    @NotEmpty
    @Column
    private String birth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotEmpty
    private String address;

    public Member(String loginId, String password, String name, String birth, Gender gender, String address) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.address = address;
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public Member() {

    }
}

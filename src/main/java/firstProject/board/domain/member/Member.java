package firstProject.board.domain.member;

import firstProject.board.domain.BaseEntity;
import firstProject.board.domain.post.Comment;
import firstProject.board.domain.post.Post;
import firstProject.board.repository.member.dto.MemberUpdateDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@ToString
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; //데이터 베이스에 관리되는 id
    @Column(unique = true)
    private String loginId;
    private String password;
    private String email;
    private String name; //사용자 이름
    private String birth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Embedded
    private Address address;



    public Member(String loginId, String password,String email, String name, String birth, Gender gender, Address address) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.address = address;
    }
    public Member(Long id, String loginId, String password,String email, String name, String birth, Gender gender, Address address) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
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

    public void update(MemberUpdateDto memberUpdateDto) {
        this.name = memberUpdateDto.getName();
        this.birth = memberUpdateDto.getBirth();
        this.email = memberUpdateDto.getEmail();
        this.address = memberUpdateDto.getAddress();
    }
}

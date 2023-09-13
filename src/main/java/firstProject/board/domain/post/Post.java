package firstProject.board.domain.post;

import firstProject.board.domain.BaseEntity;
import firstProject.board.domain.member.Member;
import firstProject.board.repository.post.dto.PostAddDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id; // 글 번호, 데이터 베이스에 관리되는 id

    private Long postNum;

    @NotBlank
    private String postName; // 글 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

    @NotBlank
    private String content;  // 글 내용

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    private Long readCount; //조회수

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UploadFile> files = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Category category;
    public Post() {
    }

    public Post(PostAddDto p) {
        this.postName = p.getPostName();
        this.content = p.getContent();
        this.category = p.getCategory();
        this.readCount = 0L;
    }
    //테스트용 id
    public Post(Long id,String postName, String content) {
        this.id = id;
        this.postName = postName;
        this.content = content;
        this.readCount = 0L;
    }

    public void updatePost(String postName, String content) {
        this.postName = postName;
        this.content = content;
    }

    public void updateMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    public void updateReadCount() {

        this.readCount += 1L;
    }

    public void updatePostNum(Long cnt) {

        this.postNum = cnt;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", postNum=" + postNum +
                ", postName='" + postName + '\'' +
                ", content='" + content + '\'' +
                ", readCount=" + readCount +
                '}';
    }
}
package firstProject.board.domain.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id; // 글 번호, 데이터 베이스에 관리되는 id

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
    private String name;  //작성자의 이름
    @NotBlank
//    @Column(name = "post_name")
    private String postName; // 글 이름

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<UploadFile> files;

    @NotBlank
    private String content;  // 글 내용

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;
    private Long readCount; //조회수
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime localDateTime;


    public Post() {
    }

    public Post(String postName, String content) {
        this.postName = postName;
        this.content = content;
        this.localDateTime = LocalDateTime.now();
        this.readCount = 0L;
    }

    public void updatePost(String postName, String content) {
        this.postName = postName;
        this.content = content;
    }

}
package firstProject.board.domain.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Entity
public class Post {
//    @Column(name = "post_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 글 번호, 데이터 베이스에 관리되는 id
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
    private String name;  //작성자의 이름
    @NotBlank
//    @Column(name = "post_name")
    private String postName; // 글 이름
    @NotBlank
    private String content;  // 글 내용

//    private Map<Long, Comment> commentList= new HashMap<>();
//    @Column(name = "read_count")
    private Long readCount; //조회수

    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Column(name = "local_date_time")
    private LocalDateTime localDateTime;


    public Post() {
    }

    public Post(String postName, String content) {
//        this.name = name;
        this.postName = postName;
        this.content = content;
        this.localDateTime = LocalDateTime.now();
    }

//    public Post(Map<Long, Comment> commentList) {
//        this.commentList = commentList;
//    }
}
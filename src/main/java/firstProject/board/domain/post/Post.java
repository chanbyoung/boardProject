package firstProject.board.domain.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class Post {
    private Long id; // 글 번호, 데이터 베이스에 관리되는 id
    private String name;  //작성자의 이름
    @NotBlank
    private String postName; // 글 이름
    @NotBlank
    private String content;  // 글 내용

    private Map<Long, Comment> commentList= new HashMap<>();

    private Long readCount; //조회수

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime localDateTime;


    public Post() {
    }

    public Post(String name, String postName, String content) {
        this.name = name;
        this.postName = postName;
        this.content = content;
        this.localDateTime = LocalDateTime.now();
    }

    public Post(Map<Long, Comment> commentList) {
        this.commentList = commentList;
    }
}
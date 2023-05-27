package firstProject.board.domain.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Post {
    private Long id; // 글 번호, 데이터 베이스에 관리되는 id
    private String name;  //작성자의 이름
    @NotBlank
    private String postName; // 글 이름
    @NotBlank
    private String content;  // 글 내용
    private Integer readCount; //조회수

    public Post() {
    }

    public Post(String name, String postName, String content, Integer readCount) {
        this.name = name;
        this.postName = postName;
        this.content = content;
        this.readCount = readCount;
    }
}

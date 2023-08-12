package firstProject.board.repository.post.dto;

import firstProject.board.domain.post.UploadFile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileGetDto {

    private Long id;
    private Long postId;
    private String uploadFileName;

    public FileGetDto(UploadFile f, Long postId) {
        this.id = f.getId();
        this.postId = postId;
        this.uploadFileName = f.getUploadFileName();
    }
}

package firstProject.board.domain.post;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class UploadFile{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id")
    private Post post;
    private String uploadFileName; //고객이 업로드한 파일명
    private String storeFileName; //서버 내부에서 관리하는 파일명
    private String fullPath; //fileService 에서 저장

    public UploadFile(String uploadFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = createStoreFileName(uploadFileName);
    }

    public UploadFile() {
    }

    public void updatePostAndFullPath(Post post, String fullPath) {
        this.post = post;
        this.fullPath = fullPath;
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid+ "." +ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }



}

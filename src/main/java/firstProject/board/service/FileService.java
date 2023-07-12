package firstProject.board.service;

import firstProject.board.domain.post.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    void saveFile(Post post, MultipartFile file) throws IOException;

    Long deleteFile(Long fileId);


}

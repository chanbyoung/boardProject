package firstProject.board.domain.post.repository;

import firstProject.board.domain.post.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<UploadFile, Long> {
    UploadFile findByPostId(Long id);
}

package firstProject.board.service;

import firstProject.board.domain.post.repository.FileRepository;
import firstProject.board.domain.post.Post;
import firstProject.board.domain.post.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
@RequiredArgsConstructor
@Slf4j
@Repository
@Transactional
public class FileServiceImpl implements FileService{
    private final FileRepository fileRepository;

    @Value("${file.dir}")
    private String fileDir;
    @Override
    public void saveFile(Post post, MultipartFile file) throws IOException {
        UploadFile uploadFile = new UploadFile(file.getOriginalFilename());
        uploadFile.setPost(post);
        fileRepository.save(uploadFile);
        String fullPath = fileDir+uploadFile.getStoreFileName();
        uploadFile.setFullPath(fullPath);
        log.info("파일 저장 fullPath={}", fullPath);
        file.transferTo(new File(fullPath));
    }

    @Override
    public void deleteFile(Post post) {
        post.getFiles().remove(0);
    }
}

package firstProject.board.service;

import firstProject.board.domain.post.Post;
import firstProject.board.domain.post.UploadFile;
import firstProject.board.repository.post.FileRepository;
import firstProject.board.repository.post.dto.FileGetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class FileServiceImpl implements FileService{
    private final FileRepository fileRepository;

    @Value("${file.dir}")
    private String fileDir;
    @Override
    public void saveFile(Post post, MultipartFile file) throws IOException {
        UploadFile uploadFile = new UploadFile(file.getOriginalFilename());
        String fullPath = fileDir+uploadFile.getStoreFileName();
        uploadFile.updatePostAndFullPath(post,fullPath);
        fileRepository.save(uploadFile);
        log.info("파일 저장 fullPath={}", fullPath);
        file.transferTo(new File(fullPath));
    }

    @Override
    public Long deleteFile(Long id) {
        UploadFile file = fileRepository.findById(id).get();
        Post post = file.getPost();
        File f = new File(file.getFullPath());
        f.delete();
        fileRepository.delete(file);
        return post.getId();
    }

    @Override
    public FileGetDto getFile(Long postId) {
        UploadFile file = fileRepository.findByPostId(postId);
        if (file == null) {
            return null;
        }
        return new FileGetDto(file, postId);
    }
}

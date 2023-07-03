package firstProject.board.service;

import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Comment;
import firstProject.board.domain.post.FileRepository;
import firstProject.board.domain.post.Post;
import firstProject.board.domain.post.UploadFile;
import firstProject.board.domain.post.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final FileRepository fileRepository;
    @Value("${file.dir}")
    private String fileDir;

    @Override
    public List<Post> getPosts(PostSearchCond cond) {
        List<Post> posts = postRepository.findAll(cond);
        return posts;
    }

    @Override
    public void saveFile(Post post, MultipartFile file) throws IOException {
        UploadFile uploadFile = new UploadFile(file.getOriginalFilename());
        uploadFile.setPost(post);
        fileRepository.save(uploadFile);
        String fullPath = fileDir+ uploadFile.getStoreFileName();
        log.info("파일 저장 fullPath={}", fullPath);
        file.transferTo(new File(fullPath));

    }

    @Override
    public Post savePost(Post post, Member member) {
        post.setMember(member);
        Post savePost = postRepository.save(post);
        return savePost;
    }

    @Override
    public void deletePost(Long id) {
        postRepository.delete(id);
    }

    @Override
    public void editPost(Long id, PostUpdateDto editParam) {
        postRepository.update(id,editParam);
    }

    @Override
    public void saveComment(Long id, Member member, CommentDto commentDto) {
        Post findPost = postRepository.findById(id);
        Comment comment = new Comment(member, commentDto.getContent(), findPost);
        commentRepository.save(comment);
    }

    @Override
    public Long deleteComment( Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        Post post = comment.getPost();
        commentRepository.delete(comment);
        return post.getId();
    }
}

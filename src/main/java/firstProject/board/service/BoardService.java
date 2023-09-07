package firstProject.board.service;

import firstProject.board.domain.post.Post;
import firstProject.board.repository.post.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface BoardService {
    PostGetDto getPost(Long id);

    //목록 조회
    Page<PostsGetDto> getPosts(PostSearchCond postSearch, Pageable pageable);

    //게시글 저장
    Long savePost(Post post, String loginId);

    //게시글 수정
    void editPost(Long id, PostUpdateDto editParam);

    void deletePost(Long id);

    Page<CommentGetDto> getComment(Long postId, Pageable pageable);

    void saveComment(Long id, String loginId, CommentDto commentDto);

    Boolean deleteComment(Long commentId, Long postId, Authentication authentication);

    void updateComment(Long commentId, String content);


    void viewCountUp(Long id);

}

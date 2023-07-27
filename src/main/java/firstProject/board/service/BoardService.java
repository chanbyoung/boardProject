package firstProject.board.service;

import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Post;
import firstProject.board.repository.post.impl.CommentDto;
import firstProject.board.repository.post.impl.PostGetDto;
import firstProject.board.repository.post.impl.PostSearchCond;
import firstProject.board.repository.post.impl.PostUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    //목록 조회
    Page<PostGetDto> getPosts(PostSearchCond postSearch, Pageable pageable);

    //게시글 저장
    Long savePost(Post post, Member member);

    //게시글 수정
    void editPost(Long id, PostUpdateDto editParam);

    void deletePost(Long id);

    void saveComment(Long id, Member member, CommentDto commentDto);

    Long deleteComment(Long commentId);

    void updateReadCount(Post post, long id);

}

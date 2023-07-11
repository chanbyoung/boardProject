package firstProject.board.service;

import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Post;
import firstProject.board.repository.post.CommentDto;
import firstProject.board.repository.post.PostSearchCond;
import firstProject.board.repository.post.PostUpdateDto;

import java.util.List;

public interface BoardService {

    //목록 조회
    List<Post> getPosts(PostSearchCond postSearch);

    //게시글 저장
    Post savePost(Post post, Member member);

    //게시글 수정
    void editPost(Long id, PostUpdateDto editParam);

    void deletePost(Long id);

    void saveComment(Long id, Member member, CommentDto commentDto);

    Long deleteComment(Long commentId);

    void updateReadCount(Post post, long id);

}

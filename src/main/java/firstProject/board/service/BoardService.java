package firstProject.board.service;

import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Post;
import firstProject.board.domain.post.PostSearchCond;

import java.util.List;

public interface BoardService {

    //목록 조회
    List<Post> getPosts(PostSearchCond postSearch);

    //게시글 저장
    Post savePost(Post post, Member member);

    //게시글 수정
    void editPost(Long id, Post editParam);

}

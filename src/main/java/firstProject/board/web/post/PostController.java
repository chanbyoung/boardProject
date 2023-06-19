package firstProject.board.web.post;

import firstProject.board.SessionConst;
import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Post;
import firstProject.board.domain.post.repository.PostRepository;
import firstProject.board.domain.post.repository.PostSearchCond;
import firstProject.board.domain.post.repository.PostUpdateDto;
import firstProject.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;
    private final BoardService boardService;

    @GetMapping
    public String posts(@ModelAttribute("postSearch") PostSearchCond postSearch, Model model){
        List<Post> posts = boardService.getPosts(postSearch);
        model.addAttribute("posts", posts);
        return "posts/posts";
    }

    @GetMapping("/{id}")
    public String post(@PathVariable long id, Model model){
        Post post = postRepository.findById(id);


//        postRepository.updateReadCount(id);
        model.addAttribute("post", post);
//        Map<Long, Comment> commentList = post.getCommentList();
//        model.addAttribute("comment", new Comment());
        return "posts/post";
    }

    @GetMapping("/add")
    public String addForm(Model model){

        model.addAttribute("post", new Post());
        return "posts/addForm";
    }

    @PostMapping("/add")
    public String addPost(@Validated @ModelAttribute Post post,
                          BindingResult bindingResult, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "posts/addForm";
        }

        //성공 로직
        log.info("loginMember name = {}", member.getName());
        Post savePost = boardService.savePost(post, member);
        redirectAttributes.addAttribute("id", savePost.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/posts/{id}";
    }
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, @SessionAttribute(name = SessionConst.LOGIN_MEMBER , required = false) Member loginMember)
    {
        Post post = postRepository.findById(id);
        model.addAttribute("post", post);
        if(loginMember.getName().equals(post.getName())){
            return "posts/editForm";
        }
        model.addAttribute("status2", true);
//        model.addAttribute("comment", new Comment());
        log.info("잘못된 요청입니다");
        return "/posts/post";

    }


    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @Validated @ModelAttribute PostUpdateDto postUpdateDto){
        boardService.editPost(id, postUpdateDto);
        return "redirect:/posts/{id}";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, @SessionAttribute(name = SessionConst.LOGIN_MEMBER , required = false) Member loginMember){
        Post findPost = postRepository.findById(id);
        if(loginMember.getName().equals(findPost.getName())){
            boardService.deletePost(id);
            return "redirect:/posts";
        }
        return "/posts/post";

    }
//    @PostMapping("/{id}/comment")
//    public String addComment(@PathVariable Long id, @ModelAttribute Comment comment, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member){
//        Post post = postRepository.findById(id);
//        comment.setName(member.getName());
////        postRepository.saveComment(post, comment);
//        return "redirect:/posts/{id}";
//    }

}

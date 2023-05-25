package firstProject.board.web.controller;

import firstProject.board.domain.post.Post;
import firstProject.board.domain.post.PostRepository;
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

    @GetMapping
    public String posts(Model model){
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "posts/posts";
    }

    @GetMapping("/{id}")
    public String post(@PathVariable long id, Model model){
        Post post = postRepository.findById(id);
        model.addAttribute("post", post);
        return "posts/post";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("post", new Post());
        return "posts/addForm";
    }
    @PostMapping("/add")
    public String addPost(@Validated @ModelAttribute Post post, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "posts/addForm";
        }

        //성공 로직
        Post savePost = postRepository.save(post);
        redirectAttributes.addAttribute("id" , savePost.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/posts/{id}";
    }
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model){
        Post post = postRepository.findById(id);
        model.addAttribute("post", post);
        return "posts/editForm";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @Validated @ModelAttribute Post editInf){
        Post postParam = new Post();
        postParam.setPostName(editInf.getPostName());
        postParam.setContent(editInf.getContent());
        postRepository.update(id, postParam);
        return "redirect:/posts/{id}";
    }
}

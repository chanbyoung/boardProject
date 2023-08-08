package firstProject.board.web.post;

import firstProject.board.SessionConst;
import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Comment;
import firstProject.board.domain.post.Post;
import firstProject.board.domain.post.UploadFile;
import firstProject.board.repository.post.CommentRepository;
import firstProject.board.repository.post.FileRepository;
import firstProject.board.repository.post.PostRepository;
import firstProject.board.repository.post.dto.*;
import firstProject.board.service.BoardService;
import firstProject.board.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;
    private final BoardService boardService;
    private final CommentRepository commentRepository;
    private final FileService fileService;
    private final FileRepository fileRepository;

    @GetMapping
    public String posts(@ModelAttribute("postSearch") PostSearchCond postSearch, Model model, @PageableDefault Pageable pageable) {
        Page<PostsGetDto> posts = boardService.getPosts(postSearch, pageable);
        PageInfo pageInfo = new PageInfo(pageable.getPageNumber(), posts.getTotalPages());
        model.addAttribute("posts", posts);
        model.addAttribute("pageInfo", pageInfo);
        return "posts/posts";
    }

    @GetMapping("/{id}")
    public String post(@PathVariable long id, Model model, @Qualifier("comment") @PageableDefault(size = 5) Pageable commentsPageable) {
        PostGetDto post = boardService.getPost(id);
        Page<Comment> comments = commentRepository.findCommentByPostId(id, commentsPageable);
        UploadFile file = fileRepository.findByPostId(id);
        log.info("file={}", file);
        model.addAttribute("file", file);
        model.addAttribute("post", post);
        model.addAttribute("comments",comments);
        model.addAttribute("commentsPageable", commentsPageable);
        model.addAttribute("commentDto", new CommentDto());
        return "posts/post";
    }

    @GetMapping("/add")
    public String addForm(Model model) {

        model.addAttribute("postAddDto", new PostAddDto());
        return "posts/addForm";
    }

    @PostMapping("/add")
    public String addPost(@Validated @ModelAttribute PostAddDto postAddDto, BindingResult bindingResult,
                          @RequestParam MultipartFile file,
                          @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member, RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "posts/addForm";
        }
        Post post = new Post(postAddDto.getPostName(), postAddDto.getContent());
        //성공 로직
        if (!file.isEmpty()) {
            fileService.saveFile(post, file);
        }
        log.info("loginMember name = {}", member.getName());
        Long postId = boardService.savePost(post, member);
        redirectAttributes.addAttribute("id", postId);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/posts/{id}";
    }






    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        Post post = postRepository.findById(id);
        model.addAttribute("postUpdateDto", new PostUpdateDto(post.getPostName(), post.getContent()));
        model.addAttribute("post", post);
        model.addAttribute("file", fileRepository.findByPostId(id));
        if (loginMember.getName().equals(post.getMember().getName())) {
            return "posts/editForm";
        }
        PostGetDto getPost = boardService.getPost(id);
        model.addAttribute("post", getPost);
        model.addAttribute("commentDto", new CommentDto());
        model.addAttribute("status2", true);
        log.info("잘못된 요청입니다");
        return "/posts/post";

    }


    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @Validated @ModelAttribute PostUpdateDto postUpdateDto, @RequestParam MultipartFile file, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "posts/editForm";
        }
        //파일 수정 로직
        if (!file.isEmpty()) {
            UploadFile findFile = fileRepository.findByPostId(id);
            if (findFile == null) {
                fileService.saveFile(postRepository.findById(id), file);
            } else {
                fileService.deleteFile(findFile.getId());
                fileService.saveFile(postRepository.findById(id), file);
            }
        }
        boardService.editPost(id, postUpdateDto);
        return "redirect:/posts/{id}";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        Post findPost = postRepository.findById(id);
        if (loginMember.getName().equals(findPost.getMember().getName())) {
            boardService.deletePost(id);
            return "redirect:/posts";
        }
        return "redirect:/posts/{id}";

    }

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id, @Validated @ModelAttribute CommentDto commentDto, BindingResult bindingResult, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "redirect:/posts/{id}";
        }
        boardService.saveComment(id, member, commentDto);
        return "redirect:/posts/{id}";
    }

    @GetMapping("/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member, RedirectAttributes redirectAttributes) {
        Long postId = boardService.deleteComment(commentId);
        redirectAttributes.addAttribute("id", postId);
        return "redirect:/posts/{id}";
    }

    @GetMapping("/attach/{fileId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long fileId) throws MalformedURLException {
        log.info("file id = {}", fileId);
        UploadFile file = fileRepository.findById(fileId).get();
        String uploadFileName = file.getUploadFileName();
        UrlResource resource = new UrlResource("file:" + file.getFullPath());
        log.info("resource = {}", resource);
        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
        log.info("contentDisposition :{}", contentDisposition);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Long fileId, RedirectAttributes redirectAttributes) {
        Long postId = fileService.deleteFile(fileId);
        redirectAttributes.addAttribute("id", postId);
        return "redirect:/posts/{id}";
    }


}





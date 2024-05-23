package edu.site.jobBook.post;

import edu.site.jobBook.post.dto.PostDTO;
import edu.site.jobBook.user.AppUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostUIController {

    private final PostService service;
    private final AppUser user1;

    public PostUIController(PostService service, @Qualifier("defaultUser1") AppUser user1) {
        this.service = service;
        this.user1 = user1;
    }

    @GetMapping("/post")
    public String getAllPosts(Model model) {
        var posts = service.fetchAllUsersPost(user1);
        model.addAttribute("posts", posts);
        model.addAttribute("postDTO", new PostDTO());
        return "post";
    }

    @PostMapping("/add-post")
    public String publishPost(@ModelAttribute PostDTO postDTO) {
        try {
            service.createPost(postDTO, user1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/post";
    }
}
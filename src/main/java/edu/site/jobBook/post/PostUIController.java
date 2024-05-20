package edu.site.jobBook.post;

import edu.site.jobBook.user.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostUIController {

    private final PostService service;
    private final User user1;

    public PostUIController(PostService service, @Qualifier("defaultUser1") User user1) {
        this.service = service;
        this.user1 = user1;
    }

    @GetMapping("/post")
    public String getAllPosts(Model model) {
        var posts = service.fetchAllUsersPost(user1);
        model.addAttribute("posts", posts);
        return "post";
    }
}

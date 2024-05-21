package edu.site.jobBook.post;

import edu.site.jobBook.post.comment.PostCommentService;
import edu.site.jobBook.post.dto.PostCommentDTO;
import edu.site.jobBook.post.dto.PostDTO;
import edu.site.jobBook.user.AppUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
@Tag(name = "User Posts", description = "API for Posts")
public class PostController {
    private final PostService postService;
    private final PostCommentService postCommentService;
    private final AppUser user1;    //for now using default user from SystemConfig
    private final AppUser user2;    //for now using default user from SystemConfig

    @Autowired
    public PostController(PostService postService, PostCommentService postCommentService, @Qualifier("defaultUser1") AppUser user1, @Qualifier("defaultUser2") AppUser user2) {
        this.postService = postService;
        this.postCommentService = postCommentService;
        this.user1 = user1;
        this.user2 = user2;
    }

    @PostMapping
    @Operation(summary = "Add a new post", description = "Creates a new post")
    public ResponseEntity<?> createPost(@RequestBody PostDTO posdDto) {
        try {
            var post = postService.createPost(posdDto, user1);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Couldn't create post");
        }
    }

    @GetMapping
    @Operation(summary = "User's Post", description = "Returns all user's post")
    public ResponseEntity<List<Post>> getUsersPost() {
        var posts = postService.fetchAllUsersPost(user1);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Post Details", description = "Get post details by id")
    public ResponseEntity<?> getPostDetails(@PathVariable String id) {
        try {
            var post = postService.fetchPostById(Long.parseLong(id));
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Couldn't get post details");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete post", description = "Delete post by id")
    public ResponseEntity<?> deletePost(@PathVariable String id) {
        try {
            postService.deletePostById(Long.parseLong(id));
            return ResponseEntity.ok("Post Deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Couldn't delete post");
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<?> addCommentToPost(@RequestBody PostCommentDTO postCommentDTO) {
        try {
            var comment = postCommentService.addComment(postCommentDTO, user2);       //user2 adding comment to post
            return ResponseEntity.ok(comment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Couldn't add comment to post");
        }
    }

    @DeleteMapping("/comment/{id}")
    @Operation(summary = "Delete comment from post", description = "Delete comment by id")
    public ResponseEntity<?> deleteComment(@PathVariable String id) {
        try {
            postCommentService.deleteComment(Long.parseLong(id));
            return ResponseEntity.ok("Comment Deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Couldn't delete comment");
        }
    }

}
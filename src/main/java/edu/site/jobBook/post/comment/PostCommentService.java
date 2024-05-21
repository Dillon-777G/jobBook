package edu.site.jobBook.post.comment;

import edu.site.jobBook.post.Post;
import edu.site.jobBook.post.PostService;
import edu.site.jobBook.post.dto.PostCommentDTO;
import edu.site.jobBook.user.AppUser;
import edu.site.jobBook.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PostCommentService {
    private final UserRepository userRepository;
    private final PostService postService;
    private final PostCommentRepository postCommentRepository;

    public PostCommentService(UserRepository userRepository, PostService postService, PostCommentRepository postCommentRepository) {
        this.userRepository = userRepository;
        this.postService = postService;
        this.postCommentRepository = postCommentRepository;
    }

    public PostComment addComment(PostCommentDTO postCommentDTO, AppUser user) {
        var oUser = userRepository.findById(user.getId());
        if(oUser.isEmpty())
            throw new IllegalArgumentException("Invalid User");
        Post post = postService.fetchPostById(Long.parseLong(postCommentDTO.getPostId()));
        if(post == null)
            throw new IllegalArgumentException("Invalid Post. Can't add your comment.");
        PostComment postComment = PostComment.builder()
                .post(post)
                .sourceUser(oUser.get())
                .time(Instant.now())
                .message(postCommentDTO.getMessage())
                .build();
        postComment =  postCommentRepository.save(postComment);
        postService.addCommentToPost(postComment);
        return postComment;
    }

    public void deleteComment(Long commentId) {
        var postComment = postCommentRepository.findById(commentId).orElse(null);
        if(postComment == null)
            throw new IllegalArgumentException("Couldn't find your comment.");
        postService.deleteCommentFromPost(postComment);
        postCommentRepository.deleteById(commentId);
    }
}
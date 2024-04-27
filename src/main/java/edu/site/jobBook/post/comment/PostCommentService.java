package edu.site.jobBook.post.comment;

import edu.site.jobBook.post.Post;
import edu.site.jobBook.post.PostService;
import edu.site.jobBook.post.dto.PostCommentDTO;
import edu.site.jobBook.user.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class PostCommentService {
    private final PostService postService;
    private final PostCommentRepository postCommentRepository;

    public PostCommentService(PostService postService, PostCommentRepository postCommentRepository) {
        this.postService = postService;
        this.postCommentRepository = postCommentRepository;
    }

    public PostComment addComment(PostCommentDTO postCommentDTO, User user) {
        Post post = postService.fetchPostById(postCommentDTO.getPostId());
        if(post == null)
            throw new IllegalArgumentException("Invalid Post. Can't add your comment.");
        PostComment postComment = PostComment.builder()
                .post(post)
                .sourceUser(user)
                .time(Instant.now())
                .message(postCommentDTO.getMessage())
                .build();
        postComment =  postCommentRepository.save(postComment);
        postService.addCommentToPost(postComment);
        return postComment;
    }

    public void deleteComment(UUID commentId) {
        var postComment = postCommentRepository.findById(commentId).orElse(null);
        if(postComment == null)
            throw new IllegalArgumentException("Couldn't find your comment.");
        postService.deleteCommentFromPost(postComment);
        postCommentRepository.deleteById(commentId);
    }
}

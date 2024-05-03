package edu.site.jobBook.post;

import edu.site.jobBook.post.comment.PostComment;
import edu.site.jobBook.post.dto.PostDTO;
import edu.site.jobBook.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> fetchAllUsersPost(User user) {
        return postRepository.findByUser(user);
    }

    public Post fetchPostById(UUID id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post createPost(PostDTO postDTO, User user) {
        Post post = Post.builder()
                .caption(postDTO.getCaption())
                .image(postDTO.getImage())
                .user(user)
                .build();
        return postRepository.save(post);
    }

    public void addCommentToPost(PostComment comment) {
        Post post = fetchPostById(comment.getPost().getId());
        if(post == null) throw new IllegalArgumentException("Invalid Post.");
        var comments = post.getComments() == null? new ArrayList<PostComment>() : post.getComments();
        comments.add(comment);
        post.setComments(comments);
        postRepository.save(post);
    }

    public void deleteCommentFromPost(PostComment comment) {
        Post post = fetchPostById(comment.getPost().getId());
        if(post == null) throw new IllegalArgumentException("Invalid Post.");
        if (post.getComments() == null) throw new IllegalArgumentException("No Comments on this Post, nothing to delete.");
        var comments = post.getComments();
        comments.remove(comment);
        post.setComments(comments);
        postRepository.save(post);
    }

    public void deletePostById(UUID id) {
        postRepository.deleteById(id);
    }
}

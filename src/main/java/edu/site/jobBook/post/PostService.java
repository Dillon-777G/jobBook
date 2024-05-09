package edu.site.jobBook.post;

import edu.site.jobBook.post.comment.PostComment;
import edu.site.jobBook.post.dto.PostDTO;
import edu.site.jobBook.user.User;
import edu.site.jobBook.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> fetchAllUsersPost(User user) {
        return postRepository.findByUser(user);
    }

    public Post fetchPostById(long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post createPost(PostDTO postDTO, User user) {
        var oUser = userRepository.findById(user.getId());
        if(oUser.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        Post post = Post.builder()
                .caption(postDTO.getCaption())
                .image(postDTO.getImage())
                .user(oUser.get())
                .build();
        post = postRepository.save(post);
        return post;
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

    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }
}

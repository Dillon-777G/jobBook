package edu.site.jobBook.postTest;

import edu.site.jobBook.post.Post;
import edu.site.jobBook.post.PostRepository;
import edu.site.jobBook.post.PostService;
import edu.site.jobBook.post.dto.PostDTO;
import edu.site.jobBook.user.AppUser;
import edu.site.jobBook.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchAllPosts() {
        List<Post> posts = List.of(new Post());
        when(postRepository.findAll()).thenReturn(posts);

        List<Post> result = postService.fetchAllPosts();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testFetchAllUsersPost() {
        AppUser user = new AppUser();
        List<Post> posts = List.of(new Post());
        when(postRepository.findByUser(any(AppUser.class))).thenReturn(posts);

        List<Post> result = postService.fetchAllUsersPost(user);
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testFetchPostById() {
        Post post = new Post();
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        Post result = postService.fetchPostById(1L);
        assertThat(result).isNotNull();
    }

    @Test
    public void testCreatePost() {
        AppUser user = new AppUser();
        user.setId(1L);
        PostDTO postDTO = new PostDTO();
        postDTO.setCaption("Caption");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(new Post());

        Post result = postService.createPost(postDTO, user);
        assertThat(result).isNotNull();
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void testCreatePost_UserNotFound() {
        AppUser user = new AppUser();
        user.setId(1L);
        PostDTO postDTO = new PostDTO();

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> postService.createPost(postDTO, user));
    }

    @Test
    public void testDeletePostById() {
        doNothing().when(postRepository).deleteById(anyLong());

        postService.deletePostById(1L);
        verify(postRepository, times(1)).deleteById(anyLong());
    }
}

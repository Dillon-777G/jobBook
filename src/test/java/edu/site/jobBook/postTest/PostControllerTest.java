package edu.site.jobBook.postTest;

import edu.site.jobBook.post.Post;
import edu.site.jobBook.post.PostController;
import edu.site.jobBook.post.PostService;
import edu.site.jobBook.post.comment.PostComment;
import edu.site.jobBook.post.comment.PostCommentService;
import edu.site.jobBook.post.dto.PostCommentDTO;
import edu.site.jobBook.post.dto.PostDTO;
import edu.site.jobBook.user.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PostControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PostService postService;

    @Mock
    private PostCommentService postCommentService;

    @Mock
    private AppUser user;

    @InjectMocks
    private PostController postController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreatePost() throws Exception {
        PostDTO postDTO = new PostDTO();
        postDTO.setCaption("Test Caption");

        Post post = Post.builder().caption("Test Caption").build();

        when(postService.createPost(any(PostDTO.class), any(AppUser.class))).thenReturn(post);

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.caption").value("Test Caption"));

        verify(postService, times(1)).createPost(any(PostDTO.class), any(AppUser.class));
    }

    @Test
    public void testGetUsersPost() throws Exception {
        Post post = Post.builder().caption("Test Caption").build();
        List<Post> posts = List.of(post);

        when(postService.fetchAllUsersPost(any(AppUser.class))).thenReturn(posts);

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].caption").value("Test Caption"));

        verify(postService, times(1)).fetchAllUsersPost(any(AppUser.class));
    }

    @Test
    public void testGetPostDetails() throws Exception {
        Post post = Post.builder().caption("Test Caption").build();

        when(postService.fetchPostById(anyLong())).thenReturn(post);

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.caption").value("Test Caption"));

        verify(postService, times(1)).fetchPostById(anyLong());
    }

    @Test
    public void testDeletePost() throws Exception {
        doNothing().when(postService).deletePostById(anyLong());

        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Post Deleted"));

        verify(postService, times(1)).deletePostById(anyLong());
    }

    @Test
    public void testAddCommentToPost() throws Exception {
        PostCommentDTO postCommentDTO = new PostCommentDTO();
        postCommentDTO.setMessage("Test Comment");
        postCommentDTO.setPostId("1");

        when(postCommentService.addComment(any(PostCommentDTO.class), any(AppUser.class)))
                .thenReturn(new PostComment());

        mockMvc.perform(post("/api/posts/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCommentDTO)))
                .andExpect(status().isOk());

        verify(postCommentService, times(1)).addComment(any(PostCommentDTO.class), any(AppUser.class));
    }

    @Test
    public void testDeleteComment() throws Exception {
        doNothing().when(postCommentService).deleteComment(anyLong());

        mockMvc.perform(delete("/api/posts/comment/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment Deleted"));

        verify(postCommentService, times(1)).deleteComment(anyLong());
    }
}

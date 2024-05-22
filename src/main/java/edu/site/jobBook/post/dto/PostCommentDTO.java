package edu.site.jobBook.post.dto;

import lombok.Data;

@Data
public class PostCommentDTO {
    private String message;
    private String postId;
}
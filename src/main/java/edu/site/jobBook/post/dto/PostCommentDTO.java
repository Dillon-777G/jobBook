package edu.site.jobBook.post.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PostCommentDTO {
    private String message;
    private UUID postId;
}

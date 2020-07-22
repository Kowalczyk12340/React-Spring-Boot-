package com.example.backendblog.model.dto;

import com.example.backendblog.model.domain.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {

    private Long id;
    private String title;
    private String body;
    private Long userId;
    private String userName;
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;

    public PostDto () {}

    public PostDto (Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.body = post.getBody();
        this.createdBy = post.getCreatedBy();
        this.createdDate = post.getCreatedDate();
        this.lastModifiedBy = post.getLastModifiedBy();
        this.lastModifiedDate = post.getLastModifiedDate();
        this.userId = post.getUser().getId();
        this.userName = post.getUser().getUserName();
    }
}

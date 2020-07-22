package com.example.backendblog.service;

import com.example.backendblog.common.Exception.BadRequestException;
import com.example.backendblog.model.domain.Comment;
import com.example.backendblog.model.domain.Post;
import com.example.backendblog.model.domain.User;
import com.example.backendblog.model.dto.CommentDto;
import com.example.backendblog.repository.CommentRepository;
import com.example.backendblog.repository.PostRepository;
import com.example.backendblog.security.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService
{
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public Optional<Comment> findForId(Long id)
    {
        return commentRepository.findById(id);
    }

    public Optional<Post> findPostForId(Long id) {
        return postRepository.findById(id);
    }

    public Optional<List<Comment>> findCommentsByPostId(Long id)
    {
        return commentRepository.findByPostId(id);
    }

    public CommentDto registerComment(CommentDto commentDto,CustomUserDetails customUserDetails)
    {
        Optional<Post> postForId = this.findPostForId(commentDto.getPostId());
        if(postForId.isPresent()) {
            Comment newComment = new Comment();
            newComment.setBody(commentDto.getBody());
            newComment.setPost(postForId.get());
            newComment.setUser(new User(customUserDetails.getId(),customUserDetails.getName()));
            return new CommentDto(commentRepository.saveAndFlush(newComment));
        }
        else {
            throw new BadRequestException("Not exist post.");
        }
    }

    public Optional<CommentDto> editPost(CommentDto editCommentDto) {
        return this.findForId(editCommentDto.getId())
                .map(comment -> {
                    comment.setBody(editCommentDto.getBody());
                    return comment;
                })
                .map(CommentDto::new);
    }

    public void deletePost(Long id)
    {
        commentRepository.findById(id).ifPresent(comment -> {
            commentRepository.delete(comment);
        });
    }
}

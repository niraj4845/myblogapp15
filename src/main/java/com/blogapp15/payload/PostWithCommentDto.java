package com.blogapp15.payload;

import com.blogapp15.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PostWithCommentDto {
    private Post post;
    private List<CommentDto> commentDtos;


    public List<CommentDto> getCommentDtos() {
        return commentDtos;
    }

    public void setCommentDtos(List<CommentDto> commentDtos) {
        this.commentDtos = commentDtos;
    }
}

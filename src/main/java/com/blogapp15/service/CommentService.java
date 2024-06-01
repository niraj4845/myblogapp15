package com.blogapp15.service;

import com.blogapp15.payload.CommentDto;
import com.blogapp15.payload.PostWithCommentDto;

public interface CommentService {
    CommentDto create(CommentDto commentDto,long postId);
    PostWithCommentDto getAllCommentByPostId(long postId);
}

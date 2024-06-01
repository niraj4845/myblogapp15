package com.blogapp15.controller;

import com.blogapp15.payload.CommentDto;
import com.blogapp15.payload.PostWithCommentDto;
import com.blogapp15.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("{postId}")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable long postId){
        CommentDto dto = commentService.create(commentDto, postId);
        return  new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public  ResponseEntity<PostWithCommentDto> getallcommentsbyid(@PathVariable long id){
        PostWithCommentDto postWithCommentDto = commentService.getAllCommentByPostId(id);
        return  new ResponseEntity<>(postWithCommentDto,HttpStatus.OK);

    }
}

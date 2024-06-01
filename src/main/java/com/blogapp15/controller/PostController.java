package com.blogapp15.controller;

import com.blogapp15.payload.ListPostDto;
import com.blogapp15.payload.PostDto;
import com.blogapp15.service.PostService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<String> delete(@PathVariable long id){
        postService.deletepost(id);
        return  new ResponseEntity<>("DELETE",HttpStatus.OK);
    }
    //http://localhost:8080/api/v1/posts?pageNo=1&pageSize=10&sortBy=description

    @GetMapping
    public ListPostDto fetchallposts(
            @RequestParam(name ="pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(name = "pageSize",defaultValue = "5",required = false)int pageSize,
            @RequestParam(name = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(name = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        ListPostDto listPostDto = postService.fetchallpost(pageNo,pageSize,sortBy,sortDir);
        return listPostDto;
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        PostDto postDto = postService.getPostId(id);
        return  new ResponseEntity<>(postDto,HttpStatus.OK);
    }

}

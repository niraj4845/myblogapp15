package com.blogapp15.service.Impl;

import com.blogapp15.entity.Comment;
import com.blogapp15.entity.Post;
import com.blogapp15.payload.CommentDto;
import com.blogapp15.payload.PostWithCommentDto;
import com.blogapp15.repository.CommentRepository;
import com.blogapp15.repository.PostRepository;
import com.blogapp15.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class CommentSeviceImpl implements CommentService {
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;
    private PostRepository postRepository;

    public CommentSeviceImpl(CommentRepository commentRepository, ModelMapper modelMapper, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto create(CommentDto commentDto,long postId) {
        Optional<Post> byId = postRepository.findById(postId);
        Post post = byId.get();
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        CommentDto dto = mapToDto(savedComment);
        return dto;
    }

    @Override
    public PostWithCommentDto getAllCommentByPostId(long postId) {
        Optional<Post> byId = postRepository.findById(postId);
        Post post = byId.get();
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> dtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        PostWithCommentDto postWithCommentDto=new PostWithCommentDto();
        postWithCommentDto.setCommentDtos(dtos);
        postWithCommentDto.setPost(post);
        return  postWithCommentDto;
    }

    Comment mapToEntity(CommentDto dto){
         Comment comment = modelMapper.map(dto, Comment.class);
         return comment;
     }
     CommentDto mapToDto(Comment comment){
         return modelMapper.map(comment,CommentDto.class);
     }
}

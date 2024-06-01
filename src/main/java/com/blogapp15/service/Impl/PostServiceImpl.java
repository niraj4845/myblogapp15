package com.blogapp15.service.Impl;

import com.blogapp15.entity.Post;
import com.blogapp15.exception.ResourceNotFound;
import com.blogapp15.payload.ListPostDto;
import com.blogapp15.payload.PostDto;
import com.blogapp15.repository.PostRepository;
import com.blogapp15.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedpost = postRepository.save(post);
        PostDto dto = mapToDto(savedpost);
        return dto;
    }

    @Override
    public void deletepost(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public ListPostDto fetchallpost(int pageNo, int pageSize, String sortBy, String sortDir) {
        //List<Post> posts = postRepository.findAll();
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> all = postRepository.findAll(pageable);
        List<Post> posts = all.getContent();
        List<PostDto> postDtos = posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        ListPostDto listPostDto=new ListPostDto();
        listPostDto.setPostDto(postDtos);
        listPostDto.setTotalPages(all.getTotalPages());
        listPostDto.setTotalElements((int) all.getTotalElements());
        listPostDto.setFirstPage(all.isFirst());
        listPostDto.setLastPage(all.isLast());
        return listPostDto;
    }

    @Override
    public PostDto getPostId(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () ->  new ResourceNotFound("Post with id not found"+id)
        );
        return mapToDto(post);
    }

    Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }
    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;
    }
}

package com.blogapp15.service;

import com.blogapp15.payload.ListPostDto;
import com.blogapp15.payload.PostDto;

public interface PostService {
    public PostDto createPost(PostDto postDto);

    void deletepost(long id);
    ListPostDto fetchallpost(int pageNo, int pageSize, String sortBy, String sortDir);
    public  PostDto getPostId(long id);
}

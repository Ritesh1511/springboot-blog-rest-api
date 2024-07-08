package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto);

	PostResponse getAllPost(int pageNo , int pageSize);

	PostDto getPostById(long id);

	PostDto updatePost(PostDto postdto , long id);

	void deletePostByID(long id);

}

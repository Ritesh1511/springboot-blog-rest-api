package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;

import java.util.List;

public interface PostService {

	PostDto createPost(PostDto postDto);

	List<PostDto> getAllPost();

	PostDto getPostById(long id);

	PostDto updatePost(PostDto postdto , long id);

	void deletePostByID(long id);

}

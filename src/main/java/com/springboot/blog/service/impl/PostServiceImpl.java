package com.springboot.blog.service.impl;

import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.stream.Collectors;




@Service
public class PostServiceImpl implements PostService{

	private static final Logger logger = LoggerFactory.getLogger(PostService.class);

	private  PostRepository postRepository;
	
	public PostServiceImpl(PostRepository postRepository) {

		this.postRepository=postRepository;
	}
	

	@Override
	public PostDto createPost(PostDto postDto) {
		
		// convert DTO to entity
		Post post = mapToEntity(postDto);

		Post newPost = postRepository.save(post); //repository method to save the instance of entity class in db.

		// convert entity to DTO
		PostDto postResponse = mapToDto(post);

		return postResponse;
	 
	}

    @Override
	public PostResponse getAllPost(int pageNo, int pageSize) {
	// Create Pageable instance
	Pageable pageable = PageRequest.of(pageNo, pageSize);
	logger.info("------- Pageable created with pageNo: {} and pageSize: {}", pageNo, pageSize);

	// Fetch page of posts
	Page<Post> posts = postRepository.findAll(pageable);
		logger.info("------- posts: Total elements={}, Total pages={}, Content={}", posts.getTotalElements(), posts.getTotalPages(), posts.getContent());

		// Map Page<Post> to List<PostDto>
	List<Post> listOfPosts = posts.getContent();
	logger.info("------- List of posts: {}", listOfPosts);

	List<PostDto> content = listOfPosts.stream()
			.map(this::mapToDto)
			.collect(Collectors.toList());
	logger.info("------- Mapped PostDto list: {}", content);

	// Create PostResponse object
	PostResponse postResponse = new PostResponse();
	postResponse.setContent(content);
	postResponse.setPageNo(posts.getNumber());
	postResponse.setPageSize(posts.getSize());
	postResponse.setTotalpages(posts.getTotalPages());
	postResponse.setTotalElement(posts.getTotalElements());
	postResponse.setLast(posts.isLast());

	logger.info("------- Constructed PostResponse: {}", postResponse);

	return postResponse;
}


	@Override
	public PostDto getPostById(long id) {
		// Find post by id
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapToDto(post);
	}

	@Override
	public PostDto updatePost(PostDto postdto , long id) {

		Post existingPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));


		existingPost.setTitle(postdto.getTitle());
		existingPost.setContent(postdto.getContent());
		existingPost.setDescription(postdto.getDescription());

		Post updatedPost = postRepository.save(existingPost);


		return mapToDto(updatedPost);
	}

	@Override
	public void deletePostByID(long id) {

		Post existingPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(existingPost);

	}


	//incoming
	// Converted dto into entity
	private Post mapToEntity(PostDto postDto){
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		return post;
	}
    //outgoing
	// Converted entity into dto
	private PostDto mapToDto(Post post){
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setContent(post.getContent());
		postDto.setTitle(post.getTitle());
		postDto.setDescription(post.getDescription());
		return postDto;
	}


}

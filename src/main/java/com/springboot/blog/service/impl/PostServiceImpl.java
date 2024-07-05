package com.springboot.blog.service.impl;

import com.springboot.blog.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService{

	
	private PostRepository postRepository;
	
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
	public List<PostDto> getAllPost() {

		List<Post> posts = postRepository.findAll(); //postRepository expects post

		return posts.stream().map( post  -> mapToDto(post)).collect(Collectors.toList());
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

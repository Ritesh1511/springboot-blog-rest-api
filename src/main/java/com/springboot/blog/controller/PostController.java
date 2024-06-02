package com.springboot.blog.controller;


import com.springboot.blog.payload.PostDto;
import com.springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

  private PostService postService;  // we are injecting interface not a class for loose coupling.


        public PostController(PostService postService){
          this.postService=postService;
        }
//
//    When you write public PostController(PostService postService),
//    you're defining a constructor for the PostController class that takes an object of type PostService
//    as a parameter.This is known as dependency injection.

  //create blog post
  @PostMapping
  public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
    return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
  }


}

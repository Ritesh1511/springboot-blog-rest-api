package com.springboot.blog.controller;


import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppContant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping
    public PostResponse getAllposts(
        @RequestParam(name = "pageNo" , defaultValue= AppContant.DEFAULT_PAGE_NUMBER ,required = false)int pageNo,
        @RequestParam(name = "pageSize" , defaultValue=AppContant.DEFAULT_PAGE_Size ,required = false)int pageSize,
        @RequestParam(name = "sortBy" ,defaultValue=AppContant.DEFAULT_SORT_BY, required =false) String sortBy,
        @RequestParam(name = "sortDir" ,defaultValue=AppContant.DEFAULT_SORT_DIR, required =false) String sortDir
    ) {


        return postService.getAllPost(pageNo , pageSize , sortBy , sortDir);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable long id) {

            PostDto postResponse = postService.updatePost(postDto,id);
            return new ResponseEntity<>(postResponse , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable(name = "id") long id){
        postService.deletePostByID(id);
            return new ResponseEntity<>("Post Entity deleted successfully" , HttpStatus.OK);
    }


}

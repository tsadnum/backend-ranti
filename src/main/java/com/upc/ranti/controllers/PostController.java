package com.upc.ranti.controllers;

import com.upc.ranti.dtos.PostDto;
import com.upc.ranti.interfaces.IPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private IPostService postService;

    @PostMapping("/grabar")
    public ResponseEntity<PostDto> grabarPost(@RequestBody PostDto postDto) {
        log.info("Grabando post: {}", postDto.getTitulo());
        return ResponseEntity.ok(postService.grabarPost(postDto));
    }


}

package com.upc.ranti.services;

import com.upc.ranti.dtos.PostDto;
import com.upc.ranti.entities.Post;
import com.upc.ranti.interfaces.IPostService;
import com.upc.ranti.repositories.PostRepositorio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PostService implements IPostService {
    @Autowired
    private PostRepositorio postRepositorio;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Transactional
    public PostDto grabarPost(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        Post guardar = postRepositorio.save(post);
        return modelMapper.map(guardar, PostDto.class);
    }



}

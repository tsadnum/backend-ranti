package com.upc.ranti.repositories;

import com.upc.ranti.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepositorio extends JpaRepository<Post, Long> {
}

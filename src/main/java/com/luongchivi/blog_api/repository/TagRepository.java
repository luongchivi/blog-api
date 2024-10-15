package com.luongchivi.blog_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luongchivi.blog_api.entity.Tag;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
    Optional<Tag> findOneByName(String name);
}

package com.example.spring_boot_rest_API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.spring_boot_rest_API.model.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByNameIn(List<String> names);
}


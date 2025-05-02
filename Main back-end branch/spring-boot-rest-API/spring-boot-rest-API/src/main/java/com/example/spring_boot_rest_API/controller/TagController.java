package com.example.spring_boot_rest_API.controller;

import com.example.spring_boot_rest_API.dto.TagDTO;
import com.example.spring_boot_rest_API.model.Tag;
import com.example.spring_boot_rest_API.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173") // frontend CORS fix
@RestController
@RequestMapping("/api")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public ResponseEntity<List<TagDTO>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @PostMapping("/tags")
    public ResponseEntity<TagDTO> createTag(@RequestBody TagDTO tag) {
        TagDTO savedTag = tagService.saveTag(tag);
        return ResponseEntity.ok(savedTag);
    }
}

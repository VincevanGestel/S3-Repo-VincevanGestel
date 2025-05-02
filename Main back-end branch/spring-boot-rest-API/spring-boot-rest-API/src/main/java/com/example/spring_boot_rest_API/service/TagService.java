package com.example.spring_boot_rest_API.service;

import com.example.spring_boot_rest_API.dto.TagDTO;
import com.example.spring_boot_rest_API.mapper.TagMapper;
import com.example.spring_boot_rest_API.model.Tag;
import com.example.spring_boot_rest_API.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagDTO> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(TagMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TagDTO saveTag(TagDTO tagDto) {
        Tag tag = TagMapper.toEntity(tagDto);
        Tag saved = tagRepository.save(tag);
        return TagMapper.toDTO(saved);
    }

    public TagDTO getTagById(Long id) {
        return tagRepository.findById(id)
                .map(TagMapper::toDTO)
                .orElse(null);
    }
}

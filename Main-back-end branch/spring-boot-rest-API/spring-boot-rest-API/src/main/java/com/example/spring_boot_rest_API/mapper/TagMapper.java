package com.example.spring_boot_rest_API.mapper;

import com.example.spring_boot_rest_API.dto.TagDTO;
import com.example.spring_boot_rest_API.model.Tag;

public class TagMapper {

    public static TagDTO toDTO(Tag tag) {
        TagDTO dto = new TagDTO();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        return dto;
    }

    public static Tag toEntity(TagDTO dto) {
        Tag tag = new Tag();
        tag.setId(dto.getId());
        tag.setName(dto.getName());
        return tag;
    }
}

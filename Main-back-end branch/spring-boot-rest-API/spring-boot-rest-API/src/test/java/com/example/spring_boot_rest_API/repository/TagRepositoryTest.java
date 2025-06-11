package com.example.spring_boot_rest_API.repository;

import com.example.spring_boot_rest_API.model.Tag;
import com.example.spring_boot_rest_API.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TagRepositoryTest {
    @Mock
    private TagRepository tagRepository;

    @Test
    void testSaveTag() {
        Tag tag = new Tag();
        tag.setName("TestTag");
        tagRepository.save(tag);
    }
}

package com.example.spring_boot_rest_API.repository;

import com.example.spring_boot_rest_API.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan("com.example.spring_boot_rest_API.model")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    void testSaveTag() {
        Tag tag = new Tag("TestTag");
        tagRepository.save(tag);

        List<Tag> tags = tagRepository.findByNameIn(List.of("TestTag"));
        assertThat(tags).hasSize(1);
        assertThat(tags.get(0).getName()).isEqualTo("TestTag");
    }
}

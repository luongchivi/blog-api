package com.luongchivi.blog_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithMockUser;

import com.luongchivi.blog_api.dto.request.tag.TagCreationRequest;
import com.luongchivi.blog_api.dto.response.tag.TagResponse;
import com.luongchivi.blog_api.entity.Tag;
import com.luongchivi.blog_api.repository.TagRepository;
import com.luongchivi.blog_api.share.response.PageResponse;

@SpringBootTest
public class TagServiceTest {
    @Autowired
    private TagService tagService;

    @MockBean
    private TagRepository tagRepository;

    private TagCreationRequest request;

    private Tag tag;

    private TagResponse tagResponse;

    @BeforeEach
    void initData() {
        request = TagCreationRequest.builder().name("Java").build();

        tag = Tag.builder()
                .id("046ee0c8-c9f2-4262-9bb3-277dcbf73dd6")
                .name("Java")
                .build();

        tagResponse = TagResponse.builder()
                .id("046ee0c8-c9f2-4262-9bb3-277dcbf73dd6")
                .name("Java")
                .build();
    }

    @Test
    @WithMockUser(username = "Admin", authorities = "ROLE_Admin")
    void createTag_validRequest_success() {
        when(tagRepository.save(any())).thenReturn(tag);

        TagResponse tagResponse = tagService.createTag(request);

        assertThat(tagResponse.getId()).isEqualTo("046ee0c8-c9f2-4262-9bb3-277dcbf73dd6");
        assertThat(tagResponse.getName()).isEqualTo("Java");
    }

    @Test
    void getTags_validRequest_success() {
        // Mock dữ liệu trả về từ repository
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());
        List<Tag> tagList = List.of(tag);
        Page<Tag> tagPage = new PageImpl<>(tagList, pageable, 1);

        // Mock repository và mapper
        when(tagRepository.findAll(pageable)).thenReturn(tagPage);

        // Gọi phương thức service
        PageResponse<TagResponse> result = tagService.getTags(1, 10, "ASC", "createdAt");

        // Kiểm tra kết quả
        assertThat(result.getCurrentPage()).isEqualTo(1);
        assertThat(result.getPageSize()).isEqualTo(10);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getData()).hasSize(1);
        assertThat(result.getData().get(0).getName()).isEqualTo("Java");
    }

    @Test
    void getTags_invalidSortDirection_failed() {
        // Kiểm tra xem IllegalArgumentException có được throw ra khi sort direction không hợp lệ
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> tagService.getTags(1, 10, "INVALID_SORT", "createdAt"));

        assertThat(exception.getMessage()).isEqualTo("Invalid sort direction. Must be 'ASC' or 'DESC'.");
    }

    @Test
    @WithMockUser(username = "Admin", authorities = "ROLE_Admin")
    void deleteTag_validRequest_success() {
        String tagId = "0ec45d4e-af2d-4e2d-a7e3-e26b73db3551";

        // Mock the repository's delete behavior, as it's the dependency of the service
        doNothing().when(tagRepository).deleteById(tagId);

        // Call the service method (no need to mock the service itself)
        tagService.deleteTag(tagId);

        // Verify that the repository's deleteById method was called with the correct argument
        verify(tagRepository, times(1)).deleteById(tagId);
    }
}

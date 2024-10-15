package com.luongchivi.blog_api.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.luongchivi.blog_api.dto.request.tag.TagCreationRequest;
import com.luongchivi.blog_api.dto.response.tag.TagResponse;
import com.luongchivi.blog_api.entity.Tag;
import com.luongchivi.blog_api.mapper.TagMapper;
import com.luongchivi.blog_api.repository.TagRepository;
import com.luongchivi.blog_api.service.TagService;
import com.luongchivi.blog_api.share.response.PageResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    TagRepository tagRepository;

    TagMapper tagMapper;

    @PreAuthorize("hasRole('Admin')")
    public TagResponse createTag(TagCreationRequest request) {
        Tag tag = tagMapper.toTag(request);
        Tag savedTag = tagRepository.save(tag);
        return tagMapper.toTagResponse(savedTag);
    }

    public PageResponse<TagResponse> getTags(int page, int pageSize, String sortDirection, String sortBy) {
        if (!sortDirection.equalsIgnoreCase("ASC") && !sortDirection.equalsIgnoreCase("DESC")) {
            throw new IllegalArgumentException("Invalid sort direction. Must be 'ASC' or 'DESC'.");
        }

        Sort sort;
        if (sortDirection.equalsIgnoreCase("DESC")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        Page<Tag> tagsPage = tagRepository.findAll(pageable);
        List<TagResponse> tagsResponse = tagsPage.getContent().stream()
                .map(tag -> tagMapper.toTagResponse(tag))
                .toList();
        return PageResponse.<TagResponse>builder()
                .currentPage(tagsPage.getNumber() + 1)
                .totalPages(tagsPage.getTotalPages())
                .pageSize(tagsPage.getSize())
                .totalElements(tagsPage.getTotalElements())
                .data(tagsResponse)
                .build();
    }

    @PreAuthorize("hasRole('Admin')")
    public void deleteTag(String tagId) {
        tagRepository.deleteById(tagId);
    }
}

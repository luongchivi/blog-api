package com.luongchivi.blog_api.service;

import com.luongchivi.blog_api.dto.request.tag.TagCreationRequest;
import com.luongchivi.blog_api.dto.response.tag.TagResponse;
import com.luongchivi.blog_api.share.response.PageResponse;

public interface TagService {
    TagResponse createTag(TagCreationRequest request);

    PageResponse<TagResponse> getTags(int page, int pageSize, String sortDirection, String sortBy);

    void deleteTag(String tagId);
}

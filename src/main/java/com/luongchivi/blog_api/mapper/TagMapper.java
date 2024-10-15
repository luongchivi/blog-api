package com.luongchivi.blog_api.mapper;

import org.mapstruct.Mapper;

import com.luongchivi.blog_api.dto.request.tag.TagCreationRequest;
import com.luongchivi.blog_api.dto.response.tag.TagResponse;
import com.luongchivi.blog_api.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag toTag(TagCreationRequest request);

    TagResponse toTagResponse(Tag tag);
}

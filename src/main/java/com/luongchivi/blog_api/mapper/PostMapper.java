package com.luongchivi.blog_api.mapper;

import com.luongchivi.blog_api.dto.request.post.PostCreationRequest;
import com.luongchivi.blog_api.dto.response.post.PostResponse;
import com.luongchivi.blog_api.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "author", ignore = true)
    Post toPost(PostCreationRequest request);

    PostResponse toPostResponse(Post post);
}

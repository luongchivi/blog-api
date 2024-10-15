package com.luongchivi.blog_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.luongchivi.blog_api.dto.request.user.UserCreationRequest;
import com.luongchivi.blog_api.dto.request.user.UserUpdateRequest;
import com.luongchivi.blog_api.dto.response.user.UserResponse;
import com.luongchivi.blog_api.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}

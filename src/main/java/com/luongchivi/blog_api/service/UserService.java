package com.luongchivi.blog_api.service;

import com.luongchivi.blog_api.dto.request.user.UserCreationRequest;
import com.luongchivi.blog_api.dto.request.user.UserUpdateRequest;
import com.luongchivi.blog_api.dto.response.user.UserResponse;
import com.luongchivi.blog_api.share.response.PageResponse;

public interface UserService {
    UserResponse createUser(UserCreationRequest request);

    PageResponse<UserResponse> getUsers(int page, int pageSize, String sortDirection, String sortBy);

    UserResponse getUserInfo();

    UserResponse getUser(String userId);

    UserResponse updateUser(String userId, UserUpdateRequest request);

    void deleteUser(String userId);
}

package com.luongchivi.blog_api.service;

import java.util.List;

import com.luongchivi.blog_api.dto.request.role.RoleCreationRequest;
import com.luongchivi.blog_api.dto.response.role.RoleResponse;

public interface RoleService {
    RoleResponse createRole(RoleCreationRequest request);

    List<RoleResponse> getRoles();

    void deleteRole(String roleName);
}

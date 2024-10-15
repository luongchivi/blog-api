package com.luongchivi.blog_api.mapper;

import com.luongchivi.blog_api.dto.request.role.RoleCreationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.luongchivi.blog_api.dto.response.role.RoleResponse;
import com.luongchivi.blog_api.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleCreationRequest request);

    RoleResponse toRoleResponse(Role role);
}

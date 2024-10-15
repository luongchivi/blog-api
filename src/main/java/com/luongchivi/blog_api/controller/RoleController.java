package com.luongchivi.blog_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.luongchivi.blog_api.dto.request.role.RoleCreationRequest;
import com.luongchivi.blog_api.dto.response.role.RoleResponse;
import com.luongchivi.blog_api.service.RoleService;
import com.luongchivi.blog_api.share.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Role")
public class RoleController {
    RoleService roleService;

    @PostMapping()
    public ApiResponse<RoleResponse> createRole(@RequestBody RoleCreationRequest request) {
        RoleResponse role = roleService.createRole(request);
        return ApiResponse.<RoleResponse>builder().results(role).build();
    }

    @GetMapping()
    public ApiResponse<List<RoleResponse>> getRoles() {
        List<RoleResponse> roles = roleService.getRoles();
        return ApiResponse.<List<RoleResponse>>builder().results(roles).build();
    }

    @DeleteMapping("/{roleName}")
    public ApiResponse deleteRole(@PathVariable("roleName") String roleName) {
        roleService.deleteRole(roleName);
        return ApiResponse.builder().message("Delete role successfully.").build();
    }
}

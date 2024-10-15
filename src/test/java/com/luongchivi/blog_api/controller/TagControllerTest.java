package com.luongchivi.blog_api.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luongchivi.blog_api.configuration.CustomJwtDecoder;
import com.luongchivi.blog_api.dto.request.tag.TagCreationRequest;
import com.luongchivi.blog_api.dto.response.tag.TagResponse;
import com.luongchivi.blog_api.entity.Permission;
import com.luongchivi.blog_api.entity.Role;
import com.luongchivi.blog_api.entity.User;
import com.luongchivi.blog_api.service.TagService;
import com.luongchivi.blog_api.share.response.PageResponse;
import com.luongchivi.blog_api.share.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class TagControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    @MockBean
    private CustomJwtDecoder customJwtDecoder;

    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    private TagCreationRequest request;
    private TagResponse tagResponse;

    private Role userRole;
    private Role adminRole;
    private Permission userPermission;
    private Permission adminPermission;
    private User user;

    private LocalDate dateOfBirth;

    private Jwt tokenDecode;

    private final String TOKEN_BEARER = "valid.token.part";
    private PageResponse<TagResponse> tagResponsePageResponse;

    @BeforeEach
    void initData() {
        dateOfBirth = LocalDate.of(1999, 3, 6);

        request = TagCreationRequest.builder().name("Java").build();

        tagResponse = TagResponse.builder()
                .id("0ec45d4e-af2d-4e2d-a7e3-e26b73db3551")
                .name("Java")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userPermission = Permission.builder()
                .name("read")
                .description("Read permission description")
                .build();

        adminPermission = Permission.builder()
                .name("write")
                .description("Write permission description")
                .build();

        userRole = Role.builder()
                .name("User")
                .description("User role description")
                .permissions(Set.of(userPermission))
                .build();

        adminRole = Role.builder()
                .name("Admin")
                .description("Admin role description")
                .permissions(Set.of(adminPermission))
                .build();

        user = User.builder()
                .username("luongchivi060399")
                .firstName("Vi")
                .lastName("Luong Chi")
                .dateOfBirth(dateOfBirth)
                .roles(Set.of(userRole, adminRole))
                .build();

        tokenDecode = Jwt.withTokenValue(TOKEN_BEARER)
                .header("alg", "HS512")
                .subject(user.getUsername())
                .issuer("luongchivi.com")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS))
                .claim("scope", Utils.buildScope(user))
                .jti(UUID.randomUUID().toString())
                .build();

        TagResponse tagResponse1 = TagResponse.builder()
                .id("0ec45d4e-af2d-4e2d-a7e3-e26b73db3551")
                .name("Java")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        TagResponse tagResponse2 = TagResponse.builder()
                .id("10e067a8-c21b-4f41-8f0e-bada9eeede79")
                .name("Spring boot")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        TagResponse tagResponse3 = TagResponse.builder()
                .id("1736e836-c63d-4a40-9640-351c47eb80c9")
                .name("Python")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        List<TagResponse> data = List.of(tagResponse1, tagResponse2, tagResponse3);

        tagResponsePageResponse = PageResponse.<TagResponse>builder()
                .currentPage(1)
                .pageSize(10)
                .totalElements(21)
                .totalPages(3)
                .data(data)
                .build();
    }

    @Test
    void createTag_validRequest_success() throws Exception {
        when(customJwtDecoder.decode(anyString())).thenReturn(tokenDecode);

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        when(tagService.createTag(any())).thenReturn(tagResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/tags")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + TOKEN_BEARER)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("results.id").value("0ec45d4e-af2d-4e2d-a7e3-e26b73db3551"));
    }

    @Test
    void createTag_noFieldNameInBody_failed() throws Exception {
        when(customJwtDecoder.decode(anyString())).thenReturn(tokenDecode);

        TagCreationRequest request = TagCreationRequest.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        when(tagService.createTag(any())).thenReturn(tagResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/tags")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + TOKEN_BEARER)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1018))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Field [name] is required"));
    }

    @Test
    void createTag_AccessDenied_failed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        when(tagService.createTag(any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/tags")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Unauthenticated"));
    }

    @Test
    void getTags_validRequest_success() throws Exception {
        when(tagService.getTags(anyInt(), anyInt(), anyString(), anyString())).thenReturn(tagResponsePageResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/tags")
                        .param("page", "1")
                        .param("pageSize", "10")
                        .param("sort", "DESC")
                        .param("sortBy", "createdAt")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("results.currentPage").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("results.pageSize").value(10))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("results.totalElements").value(21))
                .andExpect(MockMvcResultMatchers.jsonPath("results.totalPages").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("results.data[0].id")
                        .value("0ec45d4e-af2d-4e2d-a7e3-e26b73db3551"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("results.data[0].name").value("Java"));
    }

    @Test
    void getTags_inValidParamFieldSort_failed() throws Exception {
        when(tagService.getTags(anyInt(), anyInt(), eq("AAAA"), anyString()))
                .thenThrow(new IllegalArgumentException("Invalid sort direction. Must be 'ASC' or 'DESC'."));

        // Thực hiện yêu cầu với giá trị 'sort' không hợp lệ
        mockMvc.perform(MockMvcRequestBuilders.get("/tags")
                        .param("page", "1")
                        .param("pageSize", "10")
                        .param("sort", "AAAA") // Giá trị sort không hợp lệ
                        .param("sortBy", "createdAt")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // Kiểm tra mã trả về 400
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("Invalid sort direction. Must be 'ASC' or 'DESC'.")) // Kiểm tra thông báo lỗi
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1016)); // Kiểm tra mã lỗi tùy chỉnh
    }

    @Test
    void deleteTag_validRequest_success() throws Exception {
        when(customJwtDecoder.decode(anyString())).thenReturn(tokenDecode);

        doNothing().when(tagService).deleteTag(anyString());

        mockMvc.perform(MockMvcRequestBuilders.delete("/tags/0ec45d4e-af2d-4e2d-a7e3-e26b73db3551")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + TOKEN_BEARER))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("Delete tag successfully."));
    }

    @Test
    void deleteTag_AccessDenied_failed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/tags/0ec45d4e-af2d-4e2d-a7e3-e26b73db3551")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1007))
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("Unauthenticated"));
    }
}

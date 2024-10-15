package com.luongchivi.blog_api.dto.response.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String token;

    @JsonProperty("isAuthenticated")
    boolean isAuthenticated;
}

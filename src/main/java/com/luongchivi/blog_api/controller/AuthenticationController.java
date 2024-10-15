package com.luongchivi.blog_api.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luongchivi.blog_api.dto.request.introspect.IntrospectRequest;
import com.luongchivi.blog_api.dto.response.introspect.IntrospectResponse;
import com.luongchivi.blog_api.dto.request.authentication.AuthenticationRequest;
import com.luongchivi.blog_api.dto.response.authentication.AuthenticationResponse;
import com.luongchivi.blog_api.dto.request.logout.LogoutRequest;
import com.luongchivi.blog_api.dto.request.refresh.RefreshRequest;
import com.luongchivi.blog_api.service.AuthenticationService;
import com.luongchivi.blog_api.share.response.ApiResponse;
import com.nimbusds.jose.JOSEException;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Authenticate")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder().results(result).build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().results(result).build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/refresh-token")
    ApiResponse<AuthenticationResponse> logout(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder().results(result).build();
    }
}

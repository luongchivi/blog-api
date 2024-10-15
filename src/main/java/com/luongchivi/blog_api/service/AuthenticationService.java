package com.luongchivi.blog_api.service;

import java.text.ParseException;

import com.luongchivi.blog_api.dto.request.introspect.IntrospectRequest;
import com.luongchivi.blog_api.dto.response.introspect.IntrospectResponse;
import com.luongchivi.blog_api.dto.request.authentication.AuthenticationRequest;
import com.luongchivi.blog_api.dto.response.authentication.AuthenticationResponse;
import com.luongchivi.blog_api.dto.request.logout.LogoutRequest;
import com.luongchivi.blog_api.dto.request.refresh.RefreshRequest;
import com.nimbusds.jose.JOSEException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}

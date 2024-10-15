package com.luongchivi.blog_api.service;

public interface InvalidatedTokenService {
    void deleteExpiredTokens();
}

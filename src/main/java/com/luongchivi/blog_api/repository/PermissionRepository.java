package com.luongchivi.blog_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luongchivi.blog_api.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}

package com.luongchivi.blog_api.entity;

import java.time.LocalDateTime;
import java.util.Set;

import com.luongchivi.blog_api.enums.PostStatus;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    PostStatus postStatus;

    @Column(nullable = false)
    String slug;

    LocalDateTime publicationDate;

    @ManyToOne
    User author;

    @ManyToMany
    Set<Category> categories;

    @ManyToMany
    Set<Tag> tags;
}

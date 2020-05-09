package org.example.spring.service;

import lombok.RequiredArgsConstructor;
import org.example.spring.domain.posts.Posts;
import org.example.spring.domain.posts.PostsRepository;
import org.example.spring.web.dto.PostsListResponseDto;
import org.example.spring.web.dto.PostsResponseDto;
import org.example.spring.web.dto.PostsSaveRequestDto;
import org.example.spring.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "There are no corresponding posts. id=" + id
        ));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "There are no corresponding posts. id=" + id
        ));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "There are no corresponding posts. id=" + id
        ));
        postsRepository.delete(posts);
    }
}













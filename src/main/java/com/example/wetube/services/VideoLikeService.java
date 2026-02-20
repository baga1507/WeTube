package com.example.wetube.services;

import com.example.wetube.entities.User;
import com.example.wetube.entities.Video;
import com.example.wetube.entities.VideoLike;
import com.example.wetube.entities.VideoLikeId;
import com.example.wetube.exceptions.UserNotFoundException;
import com.example.wetube.repositories.UserRepository;
import com.example.wetube.repositories.VideoLikeRepository;
import com.example.wetube.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VideoLikeService {
    private final VideoLikeRepository videoLikeRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    @Transactional
    public void like(Long videoId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));
        Video video = videoRepository.findById(videoId).orElseThrow();

        VideoLikeId id = new VideoLikeId(video.getId(), user.getId());

        if (videoLikeRepository.existsById(id)) {
            return;
        }

        VideoLike videoLike = new VideoLike(video, user);
        videoLikeRepository.save(videoLike);
    }

    @Transactional
    public void unlike(Long videoId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));
        Video video = videoRepository.findById(videoId).orElseThrow();

        VideoLikeId id = new VideoLikeId(video.getId(), user.getId());

        videoLikeRepository.deleteById(id);
    }

    public Long getVideoLikeCount(Long videoId) {
        return videoLikeRepository.countByVideoId(videoId);
    }
}

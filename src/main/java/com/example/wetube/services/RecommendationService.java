package com.example.wetube.services;

import com.example.wetube.dto.PaginatedRecommendationsDto;
import com.example.wetube.dto.VideoDto;
import com.example.wetube.entities.User;
import com.example.wetube.entities.Video;
import com.example.wetube.exceptions.UserNotFoundException;
import com.example.wetube.mappers.VideoMapper;
import com.example.wetube.repositories.SubscriptionRepository;
import com.example.wetube.repositories.UserRepository;
import com.example.wetube.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.time.temporal.ChronoUnit.HOURS;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Cacheable(value = "RECOMMENDATION_CACHE", key = "#username + '-' + #pageable.pageNumber + '-' + #pageable.getPageSize()")
    public PaginatedRecommendationsDto getRecommendations(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        Slice<Video> videoSlice = videoRepository.findAllByOrderByCreatedAtDesc(pageable);
        Set<Long> subscribedTo = subscriptionRepository.findSubscribedChannelIds(user.getId());

        List<VideoDto> sorted = videoSlice.stream()
                .sorted(Comparator.comparingDouble((Video v) -> rateVideo(v, subscribedTo)).reversed())
                .map(VideoMapper::toDto)
                .limit(20)
                .toList();

        return new PaginatedRecommendationsDto(
                sorted,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                videoSlice.hasNext()
        );
    }

    private double rateVideo(Video video, Set<Long> subscribedTo) {
        double score = 0;

        if (subscribedTo.contains(video.getId())) {
            score += 100;
        }

        score += video.getViews() * 0.01;
        score += video.getLikeCount() * 0.1;

        long hoursSinceUpload = HOURS.between(video.getCreatedAt(), LocalDateTime.now());
        score += Math.max(0, 50 - hoursSinceUpload);

        return score;
    }
}

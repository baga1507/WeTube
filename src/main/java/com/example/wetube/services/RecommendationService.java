package com.example.wetube.services;

import com.example.wetube.entities.User;
import com.example.wetube.entities.Video;
import com.example.wetube.exceptions.UserNotFoundException;
import com.example.wetube.repositories.SubscriptionRepository;
import com.example.wetube.repositories.UserRepository;
import com.example.wetube.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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

    public Slice<Video> getRecommendations(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        Slice<Video> videoSlice = videoRepository.findAllByOrderByCreatedAtDesc(pageable);
        Set<Long> subscribedTo = subscriptionRepository.findSubscribedChannelIds(user.getId());

        List<Video> sorted = videoSlice.stream()
                .sorted(Comparator.comparingDouble((Video v) -> rateVideo(v, subscribedTo)).reversed())
                .limit(20)
                .toList();

        return new SliceImpl<>(sorted, pageable, videoSlice.hasContent());
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

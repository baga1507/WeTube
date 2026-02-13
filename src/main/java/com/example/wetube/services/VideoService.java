package com.example.wetube.services;

import com.example.wetube.entities.User;
import com.example.wetube.entities.Video;
import com.example.wetube.exceptions.VideoNotSavedException;
import com.example.wetube.repositories.UserRepository;
import com.example.wetube.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    @Value("${s3.bucket-name}")
    private String bucketName;

    @Transactional
    public Video uploadVideo(MultipartFile file, String title, String description, String username) {
        String key = UUID.randomUUID() + "_" + file.getOriginalFilename();
        User user = userRepository.findByUsername(username).orElseThrow();

        try {
            s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            Video video = new Video();
            video.setFilename(key);
            video.setTitle(title);
            video.setDescription(description);
            video.setUser(user);
            return videoRepository.save(video);
        } catch (Exception e) {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build());
            throw new VideoNotSavedException("Couldn't upload the video");
        }
    }

    public String getVideoLink(Long id) {
        String filename = videoRepository.findById(id).orElseThrow().getFilename();

        GetObjectRequest request = GetObjectRequest.builder().
                bucket(bucketName)
                .key(filename)
                .build();
        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(30))
                .getObjectRequest(request)
                .build());

        return presignedRequest.url().toString();
    }

    public List<Video> getAllVideoData() {
        return videoRepository.findAll();
    }
}

package com.example.wetube.services;

import com.example.wetube.entities.Video;
import com.example.wetube.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final VideoRepository videoRepository;

    @Value("${s3.bucket-name}")
    private String bucketName;

    public void uploadVideo(MultipartFile file) throws IOException {
        s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(file.getOriginalFilename())
                    .build(),
                RequestBody.fromBytes(file.getBytes()));

        Video video = new Video();
        video.setFilename(file.getOriginalFilename());
        videoRepository.save(video);
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
}

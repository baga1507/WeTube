package com.example.wetube.repositories;

import com.example.wetube.entities.Video;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Slice<Video> findAllByOrderByCreatedAtDesc(Pageable pageable);
}

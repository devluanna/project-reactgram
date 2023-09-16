package com.react.domain.repository;

import com.react.domain.model.PhotoUser.LikesPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<LikesPhoto, Long> {
    List<LikesPhoto> findByIdPhotoPosted(Long idPhotoPosted);
    Optional<LikesPhoto> findByIdLikes(Long idLikes);
}
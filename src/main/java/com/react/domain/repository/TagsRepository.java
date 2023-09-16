package com.react.domain.repository;

import com.react.domain.model.PhotoUser.TagsPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagsRepository extends JpaRepository<TagsPhoto, Long> {
    List<TagsPhoto> findByIdPhotoPosted(Long idPhotoPosted);
}
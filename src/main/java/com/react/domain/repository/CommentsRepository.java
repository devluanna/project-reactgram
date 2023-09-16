package com.react.domain.repository;


import com.react.domain.model.PhotoUser.CommentsPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<CommentsPhoto, Long> {
    List<CommentsPhoto> findByIdPhotoPosted(Long idPhotoPosted);

    Optional<CommentsPhoto> findByIdComment(Long idComment);

}
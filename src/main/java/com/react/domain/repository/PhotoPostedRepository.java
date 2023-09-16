package com.react.domain.repository;

import com.react.domain.model.PhotoUser.PhotoPosted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoPostedRepository extends JpaRepository<PhotoPosted, Long> {

    Optional<PhotoPosted> findByIdPhotoPosted(Long IdPhotoPosted);
    List<PhotoPosted> findByIdDashboard(Long idDashboard);

}
package com.react.domain.service.ServiceImp;

import com.react.domain.model.PhotoUser.CommentsPhoto;
import com.react.domain.model.PhotoUser.LikesPhoto;
import com.react.domain.model.PhotoUser.PhotoPosted;
import com.react.domain.repository.*;
import com.react.domain.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PhotoServiceImp implements PhotoService {

    @Autowired
    PhotoPostedRepository photoPostedRepository;
    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    LikesRepository likesRepository;


    @Autowired
    DashboardRepository dashboardRepository;

    public PhotoServiceImp(PhotoPostedRepository photoPostedRepository){
        this.photoPostedRepository = photoPostedRepository;
    }

    public CommentsPhoto findByIdComment(Long idComment) {
        return commentsRepository.findByIdComment(idComment).orElseThrow(NoSuchElementException::new);
    }
    public PhotoPosted findByIdPhotoPosted(Long idPhotoPosted) {
        return photoPostedRepository.findByIdPhotoPosted(idPhotoPosted).orElseThrow(NoSuchElementException::new);
    }

    public LikesPhoto findByIdLikes(Long idLikes) {
        return likesRepository.findByIdLikes(idLikes).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public PhotoPosted update(PhotoPosted photo) {
        return photoPostedRepository.save(photo);
    }


    @Override
    public void updatePhoto(PhotoPosted photoPosted) {
        photoPostedRepository.save(photoPosted);
    }

    @Override
    public List<PhotoPosted> getPhotosByUserId(Long idDashboard) {
        return photoPostedRepository.findByIdDashboard(idDashboard);
    }

    @Override
    public void deletePhoto(PhotoPosted photo) {

        String filePath = photo.getImagePath();
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        photoPostedRepository.delete(photo);
    }

    public void deleteComment(CommentsPhoto comments) {
        commentsRepository.delete(comments);
    }

    public void deleteLikes(LikesPhoto likes) {
        likesRepository.delete(likes);
    }

}
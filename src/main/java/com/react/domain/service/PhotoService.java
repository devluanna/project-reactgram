package com.react.domain.service;

import com.react.domain.model.PhotoUser.CommentsPhoto;
import com.react.domain.model.PhotoUser.LikesPhoto;
import com.react.domain.model.PhotoUser.PhotoPosted;

import java.util.List;

public interface PhotoService {

    PhotoPosted findByIdPhotoPosted(Long idPhotoPosted);
    void deletePhoto(PhotoPosted photo);
    void deleteComment(CommentsPhoto comments);
    CommentsPhoto findByIdComment(Long idComment);
    void deleteLikes(LikesPhoto likes);
    LikesPhoto findByIdLikes(Long idLikes);
    PhotoPosted update(PhotoPosted photo);
    void updatePhoto(PhotoPosted photoPosted);
    List<PhotoPosted> getPhotosByUserId(Long id);
}
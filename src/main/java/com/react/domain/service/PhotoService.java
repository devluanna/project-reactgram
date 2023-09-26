package com.react.domain.service;

import com.react.domain.model.PhotoUser.CommentsPhoto;
import com.react.domain.model.PhotoUser.LikesPhoto;
import com.react.domain.model.PhotoUser.PhotoPosted;
import com.react.domain.model.PhotoUser.TagsPhoto;

import java.util.List;

public interface PhotoService {

    PhotoPosted findByIdPhotoPosted(Long idPhotoPosted);
    void deletePhoto(PhotoPosted photo);
    void deleteComment(CommentsPhoto comments);
    CommentsPhoto findByIdComment(Long idComment);
    void deleteLikes(LikesPhoto likes);
    LikesPhoto findByIdLikes(Long idLikes);
    PhotoPosted update(PhotoPosted photo);
    TagsPhoto create(TagsPhoto response);
    void updatePhoto(PhotoPosted photoPosted);
    // TagsPhoto findByIdTag(List<Long> idTag);
    TagsPhoto findById(Long tagId);

    List<PhotoPosted> getPhotosByUserId(Long id);
}
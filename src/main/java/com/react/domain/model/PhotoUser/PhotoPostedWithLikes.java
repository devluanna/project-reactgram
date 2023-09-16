package com.react.domain.model.PhotoUser;

import java.util.List;

public class PhotoPostedWithLikes {
    private PhotoPosted photoPosted;
    private List<LikesPhoto> likes;

    public PhotoPosted getPhotoPosted() {
        return photoPosted;}
    public void setPhotoPosted(PhotoPosted photoPosted) {
        this.photoPosted = photoPosted;}


    public List<LikesPhoto> getLikes() {
        return likes;}
    public void setLikes(List<LikesPhoto> likes) {
        this.likes = likes;}

}
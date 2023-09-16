package com.react.domain.model.PhotoUser;

import java.util.List;

public class PhotoPostedWithComments {
    private PhotoPosted photoPosted;
    private List<CommentsPhoto> comments;

    public PhotoPosted getPhotoPosted() {
        return photoPosted;}

    public void setPhotoPosted(PhotoPosted photoPosted) {
        this.photoPosted = photoPosted;}

    public List<CommentsPhoto> getComments() {
        return comments;}

    public void setComments(List<CommentsPhoto> comments) {
        this.comments = comments;}


}
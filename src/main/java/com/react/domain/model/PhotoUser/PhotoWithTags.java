package com.react.domain.model.PhotoUser;

import java.util.List;

public class PhotoWithTags {

    private PhotoPosted photoPosted;
    private List<TagsPhoto> tags;

    public void setTags(List<TagsPhoto> tags) {
        this.tags = tags;
    }

    public List<TagsPhoto> getTags() {
        return tags;
    }

    public void setPhotoPosted(PhotoPosted photoPosted) {
        this.photoPosted = photoPosted;
    }

    public PhotoPosted getPhotoPosted() {
        return photoPosted;
    }
}